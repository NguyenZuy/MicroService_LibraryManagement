package com.example.project.ui.note;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.example.project.DataManager;
import com.example.project.R;
import com.example.project.databinding.FragmentNoteBinding;
import com.example.project.entities.Receipt;
import com.example.project.ui.subFragments.SubFragmentAdapter;
import com.example.project.utils.Constants;
import com.google.android.material.tabs.TabLayout;

public class NewNotesFragment extends Fragment {
    private FragmentNoteBinding binding;

    private TabLayout tabLayout;
    private ViewPager viewPager;
    private ViewNotePagerAdapter viewNotePagerAdapter;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentNoteBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        tabLayout = binding.tabLayout;
        viewPager = binding.viewPager;

        viewNotePagerAdapter = new ViewNotePagerAdapter(getChildFragmentManager(), FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        viewNotePagerAdapter.addFragment(new LoanSlipFragment(), "Loan Slip");
        viewNotePagerAdapter.addFragment(new ReturnSlipFragment(), "Return Slip");
        viewNotePagerAdapter.addFragment(new OrderSlipFragment(), "Order Slip");
        viewNotePagerAdapter.addFragment(new ImportSlipFragment(), "Import Slip");

        viewPager.setAdapter(viewNotePagerAdapter);
        tabLayout.setupWithViewPager(viewPager);

        return root;
    }
}
