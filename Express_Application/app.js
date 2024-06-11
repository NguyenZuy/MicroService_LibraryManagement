const express = require('express');
const ejs = require('ejs');
const path = require('path');

const app = express();
const axios = require('axios');

const session = require('express-session');
const flash = require('connect-flash');
const multer = require('multer');
const fs = require('fs');
const { debug, error } = require('console');
const { format, parse, formatISO } = require('date-fns');
const { stringify } = require('querystring');
const { id } = require('date-fns/locale');

app.set('view engine', 'ejs');
app.set('views', path.join(__dirname, 'views'));

// Serve static files
app.use(express.static(path.join(__dirname, 'public')));
app.use(express.urlencoded({ extended: true }));
app.use(express.json());
app.use(session({
    secret: 'K5KXFMYQ',
    resave: false,
    saveUninitialized: true,
    cookie: { secure: false }
}));
app.use((req, res, next) => {
    res.header('Access-Control-Allow-Origin', '*');
    res.header('Access-Control-Allow-Methods', 'GET, POST, PUT, DELETE');
    res.header('Access-Control-Allow-Headers', 'Content-Type');
    next();
});

// Cấu hình multer để xử lý các tệp tải lên
const storage = multer.diskStorage({
    destination: (req, file, cb) => {
        const uploadPath = path.join(__dirname, 'uploads');
        if (!fs.existsSync(uploadPath)) {
            fs.mkdirSync(uploadPath, { recursive: true });
        }
        cb(null, uploadPath); // Thư mục lưu trữ tệp tải lên
    },
    filename: (req, file, cb) => {
        cb(null, Date.now() + path.extname(file.originalname)); // Đặt tên tệp tải lên
    }
});

const upload = multer({ storage: storage });

// Define routes
// *Home
app.get('/home', async (req, res) => {
    try {
        res.render('home');
    } catch (error) {
        console.error('Error fetching books:', error);
        res.status(500).send('Error fetching books');
    }
});
app.get('/signin', async (req, res) => {
    try {
        res.render('signin');
    } catch (error) {
        console.error('Error fetching books:', error);
        res.status(500).send('Error fetching books');
    }
});

// Acount management
{
    //* Add user
    app.post('/addUser', async (req, res) => {
        try {
            const {
                username,
                password,
            } = req.body;

            // Kiểm tra dữ liệu đã được nhập vào chưa
            if (!username || !password) {
                return res.status(400).json({ error: 'All fields are required' });
            }

            const user = {
                username,
                password,
                email: "a@gmail.com",
            };
            const response = await axios.post('http://localhost:9191/user/addUser', user);
            console.error('Error add user:', response.status);
            if (!response || response.status != 200) {
                return res.status(500).json({ error: 'Error add user' });
            }
            if (response.status == 200) {
                return res.status(200).json({ message: 'Thanh cong!' });
            }

        } catch (error) {
            console.error('Error add user:', error);
            res.status(500).send('Error add users');
        }
    });
    app.post('/login', async (req, res) => {
        try {
            const {
                username,
                password,
            } = req.body;

            // Kiểm tra dữ liệu đã được nhập vào chưa
            if (!username || !password) {
                return res.status(400).json({ error: 'All fields are required' });
            }

            const user = {
                username,
                password,
                email: "a@gmail.com",
            };
            const response = await axios.post('http://localhost:9191/user/login', user);

            if (!response || response.status != 200) {
                return res.status(500).json({ error: 'Error login' });
            }
            if (response.status == 200) {
                res.render('home');
                console.error('Error login1:', response.status);
            }

        } catch (error) {
            console.error('Error login:', error);
            res.status(500).json('Error login');
        }
    });
}


// Book management
//! Check Account
//! Watch Detail UI
{
    app.get('/books', async (req, res) => {
        try {
            const response = await axios.get('http://localhost:9191/book/getBorrowable');
            const books = response.data;
            res.render('books', { books });
        } catch (error) {
            console.error('Error fetching books:', error);
            res.status(500).send('Error fetching books');
        }
    });

    app.get('/bookManagement', async (req, res) => {
        try {
            const responseGetCategories = await axios.get('http://localhost:9191/category/getAll');
            const responseGetPublishers = await axios.get('http://localhost:9191/publisher/getAll');
            const responseGetBooks = await axios.get('http://localhost:9191/book/getAll');

            const categories = responseGetCategories.data;
            const publishers = responseGetPublishers.data;
            const books = responseGetBooks.data;

            res.render('bookManagement', { books, publishers, categories });
        } catch (error) {
            console.error('Error fetching books:', error);
            res.status(500).send('Error fetching books');
        }
    });

    //* Add book
    app.post('/bookManagement', upload.single('image'), async (req, res) => {
        try {
            const {
                id,
                title,
                authorName,
                inventoryQuantity,
                availableQuantity,
                summary,
                publishDate,
                publisherName,
                categoryName,
                status
            } = req.body;
            const imageData = req.file;

            // Kiểm tra dữ liệu đã được nhập vào chưa
            if (!id || !title || !authorName || !inventoryQuantity || !summary ||
                !publishDate || !availableQuantity || !publisherName || !categoryName || !status) {
                return res.status(400).json({ error: 'All fields are required' });
            }

            // Kiểm tra availableQuantity phải nhỏ hơn inventoryQuantity
            if (parseInt(availableQuantity) > parseInt(inventoryQuantity)) {
                return res.status(400).json({ error: 'Available quantity must be less than inventory quantity' });
            }

            if (parseInt(availableQuantity) < 0 || parseInt(inventoryQuantity) < 0) {
                return res.status(400).json({ error: 'All quantity must be greater than 0' });
            }

            // Chuyển đổi ảnh về kiểu byte
            const imageBuffer = fs.readFileSync(imageData.path);
            // Chuyển đổi ảnh về kiểu base64
            const image = imageBuffer.toString('base64');

            const book = {
                id,
                image,
                title,
                authorName,
                inventoryQuantity,
                summary,
                publishDate,
                availableQuantity,
                publisherName,
                categoryName,
                status
            };

            const response = await axios.post('http://localhost:9191/book/add', book);

            if (!response || response.status !== 200) {
                console.error('Error add book:', response);
                return res.status(500).json({ error: 'Error add book' });
            }

            res.status(200).json({ message: 'Success' });
        } catch (error) {
            console.error('Error add books:', error);
            res.status(500).send('Error add books');
        }
    });

    //* Update page
    app.get('/updateBook/:id', async (req, res) => {
        try {

            const bookId = req.params.id;
            const bookResponse = await axios.get(`http://localhost:9191/book/findById/${bookId}`);

            const book = bookResponse.data;
            // console.log(book);

            const responseGetCategories = await axios.get('http://localhost:9191/category/getAll');
            const responseGetPublishers = await axios.get('http://localhost:9191/publisher/getAll');

            const categories = responseGetCategories.data;
            const publishers = responseGetPublishers.data;

            res.render('updateBook', { book, categories, publishers });
        } catch (error) {
            console.error('Error fetching books:', error);
            res.status(500).send('Error fetching books');
        }
    });

    app.post('/updateBook', upload.single('image'), async (req, res) => {
        try {
            const {
                id,
                title,
                authorName,
                inventoryQuantity,
                availableQuantity,
                summary,
                publishDate,
                publisherName,
                categoryName,
                status
            } = req.body;
            const imageData = req.file;

            if (!id || !title || !authorName || !inventoryQuantity || !summary || !publishDate || !availableQuantity || !publisherName || !categoryName || !status) {
                return res.status(400).json({ error: 'All fields are required' });
            }

            // Kiểm tra availableQuantity phải nhỏ hơn inventoryQuantity
            if (parseInt(availableQuantity) > parseInt(inventoryQuantity)) {
                return res.status(400).json({ error: 'Available quantity must be less than inventory quantity' });
            }

            if (parseInt(availableQuantity) < 0 || parseInt(inventoryQuantity) < 0) {
                return res.status(400).json({ error: 'All quantity must be greater than 0' });
            }

            let image = "";

            // Kiểm tra xem ảnh có được chọn mới không
            if (imageData) {
                // Chuyển đổi ảnh về kiểu byte
                const imageBuffer = fs.readFileSync(imageData.path);
                // Chuyển đổi ảnh về kiểu base64
                image = imageBuffer.toString('base64');
            }

            const book = {
                id,
                image,
                title,
                authorName,
                inventoryQuantity,
                summary,
                publishDate,
                availableQuantity,
                publisherName,
                categoryName,
                status
            };

            const response = await axios.put('http://localhost:9191/book/update', book);

            if (!response.status || response.status !== 200) {
                console.error('Error add book:', response);
                return res.status(500).json({ error: 'Error add book' });
            }

            res.status(200).json({ message: 'Success' });
        } catch (error) {
            console.error('Error fetching books:', error);
            res.status(500).json('Error fetching books');
        }
    });
}

// Loan management
//! Check Account
{
    app.get('/loan', async (req, res) => {
        try {
            const response = await axios.get('http://localhost:9191/loanSlip/getAll');
            const loans = response.data;

            // Get all loan
            res.render('loan', { loans });
        } catch (error) {
            console.error('Error fetching loans:', error);
            res.status(500).send('Error fetching loans');
        }
    });


    app.post('/loan', async (req, res) => {
        try {
            const { email, phoneNumber, address, firstName, lastName } = req.body;

            if (!email || !phoneNumber || !address || !firstName || !lastName) {
                return res.status(400).json({ error: 'All fields are required' });
            }

            const loanSlip = { email, phoneNumber, address, firstName, lastName };

            req.session.loanSlip = loanSlip;

            req.session.save(() => {
                res.redirect('/addDetailLoan');
            });
        } catch (error) {
            console.error('Error fetching loans:', error);
            res.status(500).send('Error fetching loans');
        }
    });
}

// Detail loan management
//! Check Account
{
    app.get('/detailLoan/:id', async (req, res) => {
        try {
            const loanId = req.params.id;
            const getLoanByIdResponse = await axios.post(`http://localhost:9191/loanSlip/getById/${loanId}`);
            const loan = getLoanByIdResponse.data;

            const getDetailResponse = await axios.get(`http://localhost:9191/detailLoanSlip/getByLoan/${loanId}`);
            const detailLoans = getDetailResponse.data;

            detailLoans.forEach(detailLoan => {
                if (detailLoan.borrowDate) {
                    detailLoan.borrowDate = format(new Date(detailLoan.borrowDate), 'dd-MM-yyyy');
                }
                if (detailLoan.returnDate) {
                    detailLoan.returnDate = format(new Date(detailLoan.returnDate), 'dd-MM-yyyy');
                }
            });

            res.render('detailLoan', { detailLoans, loan });
        } catch (error) {
            console.error(`Error fetching loan details:`, error);
            res.status(500).send('Error fetching loan details');
        }
    });

    app.get('/addDetailLoan', async (req, res) => {
        try {
            // Get all books to choose
            const response = await axios.get('http://localhost:9191/detailLoanSlip/getBooks');
            const books = response.data;
            req.session.books = books;

            // Just load UI to add
            res.render('addDetailLoan', { books: books });
        } catch (error) {
            console.error('Error fetching add detail loan:', error);
            res.status(500).send('Error fetching add detail loan');
        }
    });

    // Add
    app.post('/addDetailLoan', upload.none(), async (req, res) => {
        try {
            // console.log(req.body);
            // Save loan slip
            const loanSlip = req.session.loanSlip;
            const books = req.session.books;
            if (!books) {
                return res.status(400).json({ error: "Books not found in session" });
            }
            if (!loanSlip) {
                return res.status(400).json({ error: "Loan slip not found in session" });
            }
            const bookNames = req.body.bookName;
            const quantities = req.body.quantity;
            const borrowDates = req.body.borrowDate;
            const returnDates = req.body.returnDate;

            // Tạo một đối tượng để ánh xạ tên sách với số lượng có sẵn
            const bookAvailability = {};
            books.forEach(book => {
                bookAvailability[book.title] = book.availableQuantity;
            });

            // Kiểm tra quantities có lớn hơn availableQuantity không
            for (let i = 0; i < bookNames.length; i++) {
                const bookName = bookNames[i];

                if (quantities[i] > bookAvailability[bookName]) {
                    return res.status(400).json({ error: `Requested quantity for book "${bookName}" exceeds available quantity` });
                }
            }

            // Kiểm tra đã thêm dòng nào chưa
            if (!bookNames || bookNames.length === 0) {
                return res.status(400).json({ error: "Don't have any data" });
            }

            for (let i = 0; i < borrowDates.length; i++) {
                // Kiểm tra dữ liệu được điền
                if (quantities[i] === '' || returnDates[i] === '') {
                    return res.status(400).json({ error: "Quantities and return dates must not be empty" });
                }

                // Kiểm tra returnDates lớn hơn borrowDates
                const borrowDate = new Date(borrowDates[i]);
                const returnDate = new Date(returnDates[i]);

                if (returnDate <= borrowDate) {
                    return res.status(400).json({ error: `Return date must be after borrow date for book: ${bookNames[i]}` });
                }
            }

            // Kiểm tra trùng lặp trong bookNames
            const bookNameSet = new Set(bookNames);
            if (bookNameSet.size !== bookNames.length) {
                return res.status(400).json({ error: "Duplicate book names found" });
            }

            // console.log('Loan Slip:', loanSlip);
            const responseAddLoan = await axios.post('http://localhost:9191/loanSlip/add', loanSlip);

            if (!responseAddLoan || responseAddLoan.status !== 200) {
                console.error('Error add loan:', responseAddLoan);
                return res.status(500).json({ error: 'Error add loan' });
            }

            // Get loan slip id
            const id = responseAddLoan.data.id;

            // Get each row
            for (let i = 0; i < bookNames.length; i++) {
                const detailLoanSlip = {
                    loanSlipId: id,
                    bookName: bookNames[i],
                    quantity: quantities[i],
                    borrowDate: borrowDates[i],
                    returnDate: returnDates[i],
                    status: "Not Return"
                };
                // console.log(detailLoanSlip);

                const responseAddDetailLoan = await axios.post('http://localhost:9191/detailLoanSlip/add', detailLoanSlip);

                if (!responseAddDetailLoan || responseAddDetailLoan.status !== 200) {
                    console.error('Error add detail loan:', responseAddLoan);
                    return res.status(500).json({ error: 'Error add detail loan' });
                }
            }

            delete req.session.loanSlip;
            delete req.session.books;

            res.status(200).json({ message: 'Success' });
        } catch (error) {
            console.error('Error add detail loan:', error);
            res.status(500).json({ error: 'Error add detail loan' });
        }
    });
}

// Return management
//! Check Account
{
    app.get('/returnSlip', async (req, res) => {
        try {
            const response = await axios.get('http://localhost:9191/returnSlip/getAll');
            const returnSlips = response.data;

            res.render('returnSlip', { returnSlips });
        } catch (error) {
            console.error('Error fetching books:', error);
            res.status(500).send('Error fetching books');
        }
    });

    // Add Return
    app.post('/returnLoan/:id', async (req, res) => {
        try {
            const loanId = req.params.id;
            const getLoanByIdResponse = await axios.post(`http://localhost:9191/loanSlip/getById/${loanId}`);
            const loan = getLoanByIdResponse.data;

            const selectedData = req.body;

            // Add return
            const returnSlip = {
                email: loan.email,
                phoneNumber: loan.phoneNumber,
                address: loan.address,
                firstName: loan.firstName,
                lastName: loan.lastName,
                loanId: loanId
            }
            // console.log(returnSlip);

            const addReturnResponse = await axios.post(`http://localhost:9191/returnSlip/add`, returnSlip);
            if (!addReturnResponse || addReturnResponse.status !== 200) {
                console.error('Error add return:', addReturnResponse);
                return res.status(500).json({ error: 'Error add return' });
            }
            const getAddReturnResponse = addReturnResponse.data;

            // Add detail return
            for (const item of selectedData) {
                let borrowDate = parse(item.borrowDate, 'dd-MM-yyyy', new Date());
                const detailReturn = {
                    returnSlipId: getAddReturnResponse.id,
                    bookName: item.bookName,
                    quantity: item.quantity,
                    borrowDate: format(borrowDate, 'yyyy-MM-dd'),
                    returnDate: format(new Date(), 'yyyy-MM-dd'),
                }
                const addDetailReturnResponse = await axios.post(`http://localhost:9191/detailReturnSlip/add`, detailReturn);

                if (!addDetailReturnResponse || addDetailReturnResponse.status !== 200) {
                    console.error('Error add detail return:', addDetailReturnResponse);
                    return res.status(500).json({ error: 'Error add detail return' });
                }

                // update detail loan status
                const bookName = item.bookName;
                const updateDetailLoanResponse = await axios.put(`http://localhost:9191/detailLoanSlip/updateDetailStatus/${loanId}/${bookName}`);
                if (!updateDetailLoanResponse || updateDetailLoanResponse.status !== 200) {
                    console.error('Error update detail loan status:', updateDetailLoanResponse);
                    return res.status(500).json({ error: 'Error update detail loan status' });
                }
            };

            return res.status(200).json({ message: 'Success' });
        } catch (error) {
            console.error(`Error fetching loan details:`, error);
            res.status(500).send('Error fetching loan details');
        }
    });
}

// Category management
//! Check Account
{
    app.get('/categoryManagement', async (req, res) => {
        try {
            const response = await axios.get('http://localhost:9191/category/getAll');
            const categories = response.data;

            res.render('categoryManagement', { categories });
        } catch (error) {
            console.error('Error fetching categories:', error);
            res.status(500).send('Error fetching categories');
        }
    });

    app.post('/categoryManagement', async (req, res) => {
        try {
            const { categoryName } = req.body;

            if (!categoryName) {
                return res.status(400).json({ error: 'Error: All fields are required' });
            }

            const category = { id: 0, categoryName }

            const response = await axios.post('http://localhost:9191/category/add', category);

            if (!response || response.status !== 200) {
                // console.error('Error add category:', response);
                return res.status(500).json({ error: 'Error add category' });
            }

            res.redirect('/categoryManagement');
        } catch (error) {
            console.error('Error fetching categories:', error);
            res.status(500).json({ error: 'Error fetching categories' });
        }
    });

    app.post('/categoryManagement/update', async (req, res) => {
        try {
            const { categoryId, categoryName } = req.body;

            if (!categoryName || !categoryId) {
                return res.status(400).json({ error: 'Error: All fields are required' });
            }

            const newCategory = { id: categoryId, categoryName: categoryName };
            // console.log(newCategory)

            // const category = { id: 0, categoryName }

            const response = await axios.put('http://localhost:9191/category/update', newCategory);

            if (!response || response.status !== 200) {
                // console.error('Error add category:', response);
                return res.status(500).json({ error: 'Error add category: The data is duplicated or there is a problem with the data base' });
            }

            res.redirect('/categoryManagement');
        } catch (error) {
            console.error('Error updating category:', error);
            res.status(500).json({ error: 'Error updating category' });
        }
    });
}

// Publisher management
//! Check Account
{
    app.get('/publisherManagement', async (req, res) => {
        try {
            const response = await axios.get('http://localhost:9191/publisher/getAll');
            const publishers = response.data;

            res.render('publisherManagement', { publishers });
        } catch (error) {
            console.error('Error fetching categories:', error);
            res.status(500).send('Error fetching categories');
        }
    });

    // Add
    app.post('/publisherManagement', async (req, res) => {
        try {
            const { publisherName } = req.body;

            if (!publisherName) {
                return res.status(400).json({ error: 'All fields are required' });
            }

            const publisher = { id: 0, publisherName }

            const response = await axios.post('http://localhost:9191/publisher/add', publisher);

            if (!response || response.status !== 200) {
                // console.error('Error add publisher:', response);
                return res.status(500).json({ error: 'Error add publisher' });
            }

            res.redirect('/publisherManagement');
        } catch (error) {
            console.error('Error add publishers:', error);
            res.status(500).json({ error: 'Error add publishers' });
        }
    });

    // Update
    app.post('/publisherManagement/update', async (req, res) => {
        try {
            const { publisherId, publisherName } = req.body;

            if (!publisherName || !publisherId) {
                return res.status(400).json({ error: 'All fields are required' });
            }

            const newPublisher = { id: publisherId, publisherName: publisherName };

            const response = await axios.put('http://localhost:9191/publisher/update', newPublisher);

            if (!response || response.status !== 200) {
                // console.error('Error add publisher:', response);
                return res.status(500).json({ error: 'Error add publisher' });
            }

            res.redirect('/publisherManagement');
        } catch (error) {
            console.error('Error add publishers:', error);
            res.status(500).json({ error: 'Error add publishers' });
        }
    });
}

// Supplier management
//! Check Account
{
    app.get('/supplier', async (req, res) => {
        try {
            const response = await axios.get('http://localhost:9191/supplier/getAll');
            const suppliers = response.data;

            res.render('supplier', { suppliers });
        } catch (error) {
            console.error('Error fetching suppliers:', error);
            res.status(500).send('Error fetching suppliers');
        }
    });

    app.post('/supplier', async (req, res) => {
        try {

            const { supplierName, supplierEmail, supplierPhoneNumber, supplierAddress, supplierStatus } = req.body;
            // Kiểm tra dữ liệu đầu vào
            if (!supplierName || !supplierEmail || !supplierPhoneNumber || !supplierAddress || !supplierStatus) {
                return res.status(400).json({ error: 'All fields are required' });
            }

            const supplier = {
                id: 0,
                name: supplierName,
                email: supplierEmail,
                phoneNumber: supplierPhoneNumber,
                address: supplierAddress,
                status: supplierStatus
            };

            const response = await axios.post('http://localhost:9191/supplier/add', supplier);

            if (!response || response.status !== 200) {
                return res.status(500).json({ error: 'Failed to add supplier' });
            }

            return res.status(200).json({ message: 'Successfully' });
        } catch (error) {
            console.error('Error add suppliers:', error);
            res.status(500).json({ error: 'Error add suppliers' });
        }
    });

    // update
    app.post('/updateSupplier', async (req, res) => {
        try {

            const { supplierId, supplierName, supplierEmail, supplierPhoneNumber, supplierAddress, supplierStatus } = req.body;
            // Kiểm tra dữ liệu đầu vào
            if (!supplierId || !supplierName || !supplierEmail || !supplierPhoneNumber || !supplierAddress || !supplierStatus) {
                return res.status(400).json({ error: 'All fields are required' });
            }

            const result = {
                id: supplierId,
                name: supplierName,
                email: supplierEmail,
                phoneNumber: supplierPhoneNumber,
                address: supplierAddress,
                status: supplierStatus
            };

            const response = await axios.put('http://localhost:9191/supplier/update', result);

            if (!response || response.status !== 200) {
                return res.status(500).json({ error: 'Failed to update supplier' });
            }

            return res.status(200).json({ message: 'Successfully' });
        } catch (error) {
            console.error('Error update suppliers:', error);
            res.status(500).json({ error: 'Error update suppliers' });
        }
    });
}

// Import slip management
{
    app.get('/importManagement', async (req, res) => {
        try {
            const supplierResponse = await axios.get("http://localhost:9191/supplier/getSuppliersForSlip");
            const importResponse = await axios.get("http://localhost:9191/importSlip/getAll");

            res.render('importManagement', { imports: importResponse.data, suppliers: supplierResponse.data });
        } catch (error) {
            console.error('Error fetching imports:', error);
            res.status(500).send('Error fetching imports');
        }
    });

    app.get('/detailImport/:id', async (req, res) => {
        try {
            const importId = req.params.id;

            const getDetailResponse = await axios.get(`http://localhost:9191/detailImportSlip/getByImportId/${importId}`);
            const detailImportSlips = getDetailResponse.data;

            res.render('detailImport', { detailImports: detailImportSlips });
        } catch (error) {
            console.error('Error fetching imports:', error);
            res.status(500).send('Error fetching imports');
        }
    });
}

// Order slip management
{
    app.get('/orderManagement', async (req, res) => {
        try {
            const supplierResponse = await axios.get("http://localhost:9191/supplier/getSuppliersForSlip");
            const orderResponse = await axios.get("http://localhost:9191/orderSlip/getAll");

            res.render('orderManagement', { orders: orderResponse.data, suppliers: supplierResponse.data });
        } catch (error) {
            console.error('Error fetching orders:', error);
            res.status(500).send('Error fetching orders');
        }
    });

    app.post('/orderManagement', async (req, res) => {
        try {
            // Lấy dữ liệu từ URL parameters
            const { orderDate, supplierName } = req.body;

            const order = {
                orderDate: orderDate,
                supplierName: supplierName
            };

            // Kiểm tra dữ liệu đầu vào
            if (!orderDate || !supplierName) {
                return res.status(400).json({ error: 'Missing orderDate or supplierName' });
            }

            req.session.order = order
            return res.status(200).json({ message: 'Success' });
        } catch (error) {
            console.error('Error fetching orders:', error);
            res.status(500).json('Error fetching orders');
        }
    });

    // UI Detail
    app.get('/detailOrder/:id', async (req, res) => {
        try {
            const orderId = req.params.id;
            const getOrderByIdResponse = await axios.post(`http://localhost:9191/orderSlip/getById/${orderId}`);
            const order = getOrderByIdResponse.data;

            const getDetailResponse = await axios.get(`http://localhost:9191/detailOrderSlip/findByOrderId/${orderId}`);
            const detailOrders = getDetailResponse.data;

            res.render('detailOrder', { detailOrders, order });
        } catch (error) {
            console.error(`Error fetching loan details:`, error);
            res.status(500).send('Error fetching loan details');
        }
    });

    // UI add
    app.get('/addDetailOrder', async (req, res) => {
        try {
            const booksResponse = await axios.get("http://localhost:9191/book/getAll");

            res.render('addDetailOrder', { books: booksResponse.data });
        } catch (error) {
            console.error('Error fetching orders:', error);
            res.status(500).json({ error: 'Error fetching orders' });
        }
    });

    // Add detail order
    app.post('/addDetailOrder', upload.none(), async (req, res) => {
        try {
            const booksResponse = await axios.get("http://localhost:9191/book/getAll");
            const order = req.session.order;

            if (!order) {
                return res.status(400).json({ error: "Order slip not found in session" });
            }

            const bookNames = req.body.bookName;
            const quantities = req.body.quantity;

            // Kiểm tra đã thêm dòng nào chưa
            if (!bookNames || bookNames.length === 0) {
                return res.status(400).json({ error: "Don't have any data" });
            }

            for (let i = 0; i < bookNames.length; i++) {
                // Kiểm tra dữ liệu được điền
                if (quantities[i] === '') {
                    return res.status(400).json({ error: "Quantities must not be empty" });
                }
            }

            // Kiểm tra trùng tên sách
            const bookNameSet = new Set(bookNames);
            if (bookNameSet.size !== bookNames.length) {
                return res.status(400).json({ error: "Duplicate book names found" });
            }

            console.log('Order Slip:', order);
            let orderSlip = {
                id: 0,
                orderDate: order.orderDate,
                supplierName: order.supplierName
            }
            const responseAddOrder = await axios.post('http://localhost:9191/orderSlip/add', order);

            if (!responseAddOrder || responseAddOrder.status !== 200) {
                console.error('Error add order:', responseAddOrder);
                return res.status(500).json({ error: 'Error add order' });
            }

            const id = responseAddOrder.data.id;

            // Get each row
            for (let i = 0; i < bookNames.length; i++) {
                const detailOrderSlip = {
                    orderSlipId: id,
                    bookName: bookNames[i],
                    quantity: quantities[i],
                    status: "Not Import"
                };

                const responseAddDetailOrder = await axios.post('http://localhost:9191/detailOrderSlip/add', detailOrderSlip);

                if (!responseAddDetailOrder || responseAddDetailOrder.status !== 200) {
                    console.error('Error add detail order:', responseAddOrder);
                    return res.status(500).json({ error: 'Error add detail order' });
                }
            }

            delete req.session.order;
            delete req.session.books;

            res.status(200).json({ message: 'Success' });
        } catch (error) {
            console.error('Error fetching orders:', error);
            res.status(500).json({ error: 'Error fetching orders' });
        }
    });
}

// Detail return management
app.get('/detailReturnSlip/:id', async (req, res) => {
    try {
        const returnId = req.params.id;
        const getDetailResponse = await axios.get(`http://localhost:9191/detailReturnSlip/getByReturn/${returnId}`);
        const detailReturnSlips = getDetailResponse.data;

        detailReturnSlips.forEach(detailReturnSlip => {
            if (detailReturnSlip.borrowDate) {
                detailReturnSlip.borrowDate = format(new Date(detailReturnSlip.borrowDate), 'dd-MM-yyyy');
            }
            if (detailReturnSlip.returnDate) {
                detailReturnSlip.returnDate = format(new Date(detailReturnSlip.returnDate), 'dd-MM-yyyy');
            }
        });

        res.render('detailReturnSlip', { detailReturnSlips });
    } catch (error) {
        console.error('Error fetching detail return:', error);
        res.status(500).send('Error fetching detail return');
    }
});

// Add Return
app.post('/importBook/:id', async (req, res) => {
    try {
        const orderId = req.params.id;
        const getOrderByIdResponse = await axios.post(`http://localhost:9191/orderSlip/getById/${orderId}`);
        const order = getOrderByIdResponse.data;

        const selectedData = req.body;

        // Add import
        const today = new Date();
        const yyyy = today.getFullYear();
        const mm = String(today.getMonth() + 1).padStart(2, '0'); // Tháng bắt đầu từ 0
        const dd = String(today.getDate()).padStart(2, '0');

        const importSlip = {
            id: 0,
            importDate: `${yyyy}-${mm}-${dd}`,
            supplierName: order.supplierName,
            idOrderSlip: order.id
        };

        console.log(importSlip);

        const addImportResponse = await axios.post(`http://localhost:9191/importSlip/add`, importSlip);
        if (!addImportResponse || addImportResponse.status !== 200) {
            console.error('Error add import:', addImportResponse);
            return res.status(500).json({ error: 'Error add import' });
        }
        const getAddImportResponse = addImportResponse.data;

        // console.log('getAddImportResponse: ', getAddImportResponse);

        // Add detail import
        for (const item of selectedData) {
            const detailImport = {
                importSlipId: getAddImportResponse.id,
                bookName: item.bookName,
                quantity: parseInt(item.quantity, 10),
            }
            // console.log('Detail import: ', detailImport);

            const addDetailImportResponse = await axios.post(`http://localhost:9191/detailImportSlip/add`, detailImport);

            if (!addDetailImportResponse || addDetailImportResponse.status !== 200) {
                console.error('Error add detail import:', addDetailImportResponse);
                return res.status(500).json({ error: 'Error add detail import' });
            }

            // update detail order status
            const bookName = item.bookName;
            const updateDetailOrderResponse = await axios.put(`http://localhost:9191/detailOrderSlip/updateStatus/${orderId}/${bookName}`);
            if (!updateDetailOrderResponse || updateDetailOrderResponse.status !== 200) {
                console.error('Error update detail order status:', updateDetailOrderResponse);
                return res.status(500).json({ error: 'Error update detail order status' });
            }
        };
        return res.status(200).json({ message: 'Success' });
    } catch (error) {
        console.error(`Error add import details:`, error);
        res.status(500).json({ error: 'Error add import details' });
    }
});

app.get('/analyst', async (req, res) => {
    try {
        res.render('analyst');
    } catch (error) {
        console.error('Error fetching books:', error);
        res.status(500).send('Error fetching books');
    }
});

app.listen(3000, () => {
    console.log('Server is listening on port 3000');
});

app.get('/test', (req, res) => {
    res.render('test');
});