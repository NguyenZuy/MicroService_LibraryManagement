package com.example.project.ui.bookManager;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.example.project.databinding.FragmentBookManagerBinding;
import com.google.android.material.tabs.TabLayout;

public class BookManagerFragment extends Fragment {
    private FragmentBookManagerBinding binding;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private ViewBookPagerAdapter viewBookPagerAdapter;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentBookManagerBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        tabLayout = binding.tabLayout;
        viewPager = binding.viewPager;
        viewBookPagerAdapter = new ViewBookPagerAdapter(getChildFragmentManager(), FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);

        // Add fragments to the adapter
        viewBookPagerAdapter.addFragment(new CategoryFragment(), "Category");
        viewBookPagerAdapter.addFragment(new PublisherFragment(), "Publisher");
        viewBookPagerAdapter.addFragment(new SupplierFragment(), "Supplier");

        viewPager.setAdapter(viewBookPagerAdapter);
        tabLayout.setupWithViewPager(viewPager);

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
