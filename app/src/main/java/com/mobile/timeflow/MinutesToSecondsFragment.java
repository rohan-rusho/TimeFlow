package com.mobile.timeflow;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;

public class MinutesToSecondsFragment extends Fragment {

    private AppCompatEditText inputValue;
    private TextView textResultPlaceholder;
    private TextView textResult;
    private AppCompatButton btnConvert, btnCopy, btnShare;

    public MinutesToSecondsFragment() {}

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_minutes_to_seconds, container, false);

        inputValue = view.findViewById(R.id.inputValue);
        textResultPlaceholder = view.findViewById(R.id.textResultPlaceholder);
        textResult = view.findViewById(R.id.textResult);
        btnConvert = view.findViewById(R.id.btnConvert);
        btnCopy = view.findViewById(R.id.btnCopy);
        btnShare = view.findViewById(R.id.btnShare);

        btnConvert.setOnClickListener(v -> {
            String input = inputValue.getText().toString().trim();
            if (TextUtils.isEmpty(input)) {
                Toast.makeText(getContext(), "Please enter minutes", Toast.LENGTH_SHORT).show();
                return;
            }

            try {
                double minutes = Double.parseDouble(input);
                double seconds = minutes * 60;
                String result = String.format("%.2f Seconds", seconds);

                textResult.setText(result);
                textResultPlaceholder.setVisibility(View.GONE);
                btnCopy.setVisibility(View.VISIBLE);
                btnShare.setVisibility(View.VISIBLE);
            } catch (NumberFormatException e) {
                Toast.makeText(getContext(), "Invalid number", Toast.LENGTH_SHORT).show();
            }
        });

        btnCopy.setOnClickListener(v -> {
            String result = textResult.getText().toString();
            if (!TextUtils.isEmpty(result)) {
                ClipboardManager clipboard = (ClipboardManager) requireContext()
                        .getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clip = ClipData.newPlainText("Time Result", result);
                clipboard.setPrimaryClip(clip);
                Toast.makeText(getContext(), "Copied to clipboard", Toast.LENGTH_SHORT).show();
            }
        });

        btnShare.setOnClickListener(v -> {
            String result = textResult.getText().toString();
            if (!TextUtils.isEmpty(result)) {
                Intent shareIntent = new Intent(Intent.ACTION_SEND);
                shareIntent.setType("text/plain");
                shareIntent.putExtra(Intent.EXTRA_TEXT, result);
                startActivity(Intent.createChooser(shareIntent, "Share via"));
            }
        });

        return view;
    }
}
