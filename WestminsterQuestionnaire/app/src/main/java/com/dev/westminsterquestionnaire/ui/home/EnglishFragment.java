package com.dev.westminsterquestionnaire.ui.home;

import android.annotation.SuppressLint;
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
import android.widget.Toast;

import com.dev.westminsterquestionnaire.DataModel.EnglishQuestions;
import com.dev.westminsterquestionnaire.DataModel.ScienceQuestions;
import com.dev.westminsterquestionnaire.R;
import com.dev.westminsterquestionnaire.databinding.FragmentEnglishBinding;

import java.util.Objects;

public class EnglishFragment extends Fragment {

    private FragmentEnglishBinding binding;
    int totalQuestion = EnglishQuestions.questionEnglish.length;
    int score = 0;
    int currentQuestionIndex = 0;
    int countIndex = 1;
    String selectedAnswer = "";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding =  FragmentEnglishBinding.inflate(inflater, container, false);

        return binding.getRoot();
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.tvCount.setText("1/10");
        binding.tvQuestion.setText(EnglishQuestions.questionEnglish[currentQuestionIndex]);
        binding.btnChoice1.setText(EnglishQuestions.choicesEnglish[currentQuestionIndex][0]);
        binding.btnChoice2.setText(EnglishQuestions.choicesEnglish[currentQuestionIndex][1]);

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
                    bundle.putString("subject", "English");
                    Navigation.findNavController(v).navigate(R.id.action_englishFragment_to_scoreFragment, bundle);

                } else {
                    if (Objects.equals(selectedAnswer, "")) {
                        Toast.makeText(requireContext(), "Please select answer first", Toast.LENGTH_SHORT).show();

                    }else{
                        countIndex = countIndex+1;
                        binding.btnChoice1.getBackground().setColorFilter(ContextCompat.getColor(requireContext(), R.color.main), PorterDuff.Mode.MULTIPLY);
                        binding.btnChoice2.getBackground().setColorFilter(ContextCompat.getColor(requireContext(), R.color.main), PorterDuff.Mode.MULTIPLY);
                        currentQuestionIndex++;
                        selectedAnswer = "";
                        binding.tvCount.setText((currentQuestionIndex+1) + "/10");
                        binding.tvQuestion.setText(EnglishQuestions.questionEnglish[currentQuestionIndex]);
                        binding.btnChoice1.setText(EnglishQuestions.choicesEnglish[currentQuestionIndex][0]);
                        binding.btnChoice2.setText(EnglishQuestions.choicesEnglish[currentQuestionIndex][1]);
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
                    if (selectedAnswer.equals(EnglishQuestions.answersEnglish[currentQuestionIndex])) {
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
                    if (selectedAnswer.equals(EnglishQuestions.answersEnglish[currentQuestionIndex])) {
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