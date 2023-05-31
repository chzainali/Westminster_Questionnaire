package com.dev.westminsterquestionnaire.ui.home;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.dev.westminsterquestionnaire.DataModel.ScienceQuestions;
import com.dev.westminsterquestionnaire.R;
import com.dev.westminsterquestionnaire.databinding.FragmentScienceBinding;

import java.util.Objects;

public class ScienceFragment extends Fragment {

    public ScienceFragment() {
        // doesn't do anything special
    }

    private FragmentScienceBinding binding;
    int totalQuestion = ScienceQuestions.questionScience.length;
    int score = 0;
    int currentQuestionIndex = 0;
    int countIndex = 1;
    String selectedAnswer = "";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @SuppressLint("SetTextI18n")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentScienceBinding.inflate(inflater, container, false);

        return binding.getRoot();
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.tvCount.setText("1/15");
        binding.tvQuestion.setText(ScienceQuestions.questionScience[currentQuestionIndex]);
        binding.btnChoice1.setText(ScienceQuestions.choicesScience[currentQuestionIndex][0]);
        binding.btnChoice2.setText(ScienceQuestions.choicesScience[currentQuestionIndex][1]);

        binding.ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Navigation.findNavController(v).navigateUp();

            }
        });

        Quiz();

    }

    private void Quiz() {

        binding.btnNext.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View v) {

                if (Objects.equals(countIndex , totalQuestion)) {

                    Bundle bundle = new Bundle();
                    bundle.putString("score", String.valueOf(score));
                    bundle.putString("subject", "Science");
                    Navigation.findNavController(v).navigate(R.id.action_scienceFragment_to_scoreFragment, bundle);

                } else {
                    if (Objects.equals(selectedAnswer, "")) {
                        Toast.makeText(requireContext(), "Please select answer first", Toast.LENGTH_SHORT).show();

                    }else{
                        countIndex = countIndex+1;
                        binding.btnChoice1.getBackground().setColorFilter(ContextCompat.getColor(requireContext(), R.color.main), PorterDuff.Mode.MULTIPLY);
                        binding.btnChoice2.getBackground().setColorFilter(ContextCompat.getColor(requireContext(), R.color.main), PorterDuff.Mode.MULTIPLY);
                        currentQuestionIndex++;
                        selectedAnswer = "";
                        binding.tvCount.setText((currentQuestionIndex+1) + "/15");
                        binding.tvQuestion.setText(ScienceQuestions.questionScience[currentQuestionIndex]);
                        binding.btnChoice1.setText(ScienceQuestions.choicesScience[currentQuestionIndex][0]);
                        binding.btnChoice2.setText(ScienceQuestions.choicesScience[currentQuestionIndex][1]);
                    }
                }

            }
        });

        binding.btnChoice1.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("UseCompatLoadingForDrawables")
            @Override
            public void onClick(View v) {

                if (Objects.equals(selectedAnswer, "")) {

                    selectedAnswer = binding.btnChoice1.getText().toString();
                    if (selectedAnswer.equals(ScienceQuestions.answersScience[currentQuestionIndex])) {
                        score++;
                        binding.btnChoice1.getBackground().setColorFilter(ContextCompat.getColor(requireContext(), R.color.green), PorterDuff.Mode.MULTIPLY);
                        binding.btnChoice2.getBackground().setColorFilter(ContextCompat.getColor(requireContext(), R.color.red), PorterDuff.Mode.MULTIPLY);
                    } else {
                        binding.btnChoice1.getBackground().setColorFilter(ContextCompat.getColor(requireContext(), R.color.red), PorterDuff.Mode.MULTIPLY);
                        binding.btnChoice2.getBackground().setColorFilter(ContextCompat.getColor(requireContext(), R.color.green), PorterDuff.Mode.MULTIPLY);
                    }

                } else {

                    Toast.makeText(requireContext(), "You have already select", Toast.LENGTH_SHORT).show();

                }
            }

        });

        binding.btnChoice2.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onClick(View v) {

                if (Objects.equals(selectedAnswer, "")) {

                    selectedAnswer = binding.btnChoice2.getText().toString();
                    if (selectedAnswer.equals(ScienceQuestions.answersScience[currentQuestionIndex])) {
                        score++;
                        binding.btnChoice2.getBackground().setColorFilter(ContextCompat.getColor(requireContext(), R.color.green), PorterDuff.Mode.MULTIPLY);
                        binding.btnChoice1.getBackground().setColorFilter(ContextCompat.getColor(requireContext(), R.color.red), PorterDuff.Mode.MULTIPLY);
                    } else {
                        binding.btnChoice2.getBackground().setColorFilter(ContextCompat.getColor(requireContext(), R.color.red), PorterDuff.Mode.MULTIPLY);
                        binding.btnChoice1.getBackground().setColorFilter(ContextCompat.getColor(requireContext(), R.color.green), PorterDuff.Mode.MULTIPLY);
                    }

                } else {

                    Toast.makeText(requireContext(), "You have already select", Toast.LENGTH_SHORT).show();

                }
            }
        });

    }
}