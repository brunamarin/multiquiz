package edu.upc.eseiaat.pma.bruna.quiz;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

public class MultiQuiz_Activity extends AppCompatActivity {

    private int ids_answers[]= {
        R.id.answer1,R.id.answer2,R.id.answer3,R.id.answer4

    };
    private int correctanswer;
    private String[] all_questions;
    private int current_question;
    private TextView text_question;
    private RadioGroup group;
    private boolean[] answer_is_correct;
    private Button button_next;
    private Button button_previous;
    private int [] answer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        text_question = (TextView) findViewById(R.id.text_question);
        all_questions = getResources().getStringArray(R.array.all_questions);
        group= (RadioGroup) findViewById(R.id.answer_group);
        answer_is_correct = new boolean[all_questions.length];
        answer = new int[all_questions.length];

        for (int i=0;i< answer.length;i++){
            answer[i] = -1;
        }

        current_question=0;

        ShowQuestion(); // crida al metode

        // final int correctanswer = getResources().getInteger(R.integer.correctanswer);


        button_next =(Button) findViewById(R.id.btn_check);
        button_previous =(Button) findViewById(R.id.btn_prev);
        button_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CheckAnswer();

 /*  if (answer==correctanswer){
                    Toast.makeText(MultiQuiz_Activity.this, R.string.correct, Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(MultiQuiz_Activity.this, R.string.wrong, Toast.LENGTH_SHORT).show();
                }
                */

                if (current_question<all_questions.length-1){
                    current_question++;
                    ShowQuestion();
                }
                else {
                    int correctas = 0, incorrectas =0;
                    for (boolean b :  answer_is_correct){
                        if (b) correctas++;
                        else incorrectas++;
                    }
                    String resultado = String.format("correctas,%d - incorrectas;%d", correctas,incorrectas);
                    Toast.makeText(MultiQuiz_Activity.this, resultado, Toast.LENGTH_LONG).show();
                    finish();
                }
            }
        });

        button_previous.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (current_question>0){
                    current_question--;
                    ShowQuestion();
                }
            }
        });

    }

    private void CheckAnswer() {
        int id=group.getCheckedRadioButtonId();
        int ans=-1;
        for(int i=0; i<ids_answers.length;i++){
            if (ids_answers[i]==id){
                ans=i;
            }
        }

        answer_is_correct[current_question]=(ans==correctanswer);
        answer[current_question] =(ans);
    }


    private void ShowQuestion() {
        String q = all_questions[current_question];
        String [] parts = q.split(";");

        group.clearCheck();

        text_question.setText(parts[0]); //enlloc de text_question.setText(R.string.text_question)

        String [] answers = getResources().getStringArray(R.array.all_questions);

        for(int i=0;i<ids_answers.length;i++) {

            RadioButton rb=(RadioButton) findViewById(ids_answers[i]);
            String ans = parts[i+1];
            if (ans.charAt(0)=='*'){

                correctanswer = i;
                ans=ans.substring(1); //et dona string a partir de caràcter 1
            }
            rb.setText(String.format("Respuesta %d",i));
            rb.setText(ans);

            if (ans[current_question] == i){
                rb.setChecked(true);
            }
        }
        if (current_question==all_questions.length-1) {
            button_next.setText(R.string.finish);
        }
        else {
            button_next.setText(R.string.Next);
        }
        if (current_question==0){
            button_previous.setVisibility(View.GONE);
        }
        else {
            button_previous.setVisibility(View.VISIBLE);
        }
    }
}
