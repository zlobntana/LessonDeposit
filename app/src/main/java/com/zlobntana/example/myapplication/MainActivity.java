package com.zlobntana.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.ViewDebug;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {


    //задание полей
    float robotPrice = 35_000; // стоимость робота
    float stipend = 1_700; // стипендия в месяц
    float account = 700; //счёт пользователя
    float percentBank = 9; // годовая процентная ставка за депозит
    float [] monthlyPayments = new float[24]; // создание массива ежемесячных платежей на 2 года

    // создание дополнительных полей для вывода на экран полученных значений
    private TextView countOut; //  поле вывода количества месяцев накопления депозита
    private TextView manyMonthOut; // поле выписки по ежемесячным накоплениям

    //вывод на экран полученных значений
    @Override
    protected void onCreate(Bundle savedInstanceState) { // создание жизненного цикла активности
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main); // присваивание переменным активности элементов представления activity_main

        countOut = findViewById(R.id.countOut); // вывод информации количества месяцев накопления депозита
        manyMonthOut = findViewById(R.id.manyMonthOut); // вывод информации выписки по ежемесячным накоплениям

       // заполнение экрана
       // 1) вывод количества месяцев накопления депозита
        countOut.setText(countMonth(percentBank, stipend, robotPrice, account) + "месяцев");
// 2) подготовка выписки
        String monthlyPaymentsList = ""; // строка для записи выписки
        for (float list: monthlyPayments) { // цикл заполнения строки выпиской
            if (list > 0) {
                monthlyPaymentsList += Float.toString(list) + ", ";
            } else {
                continue;
            }
        }
        // 3) вывод выписки ежемесячных платежей для депозита
        manyMonthOut.setText("Первоначальный взнос" + account + " монет, ежемесячные платежи (монет): " + monthlyPaymentsList);
    }

    // метод подсчёта времени накопления депозита (годовой процент, стипендия,стоимость робота, накопления)
    public int countMonth (float percentBankYear,float amountMonth, float robot,float accumulation) {
        float percentBankMonth = percentBankYear / 12; //подсчёт ежемемячного процента банка за депозит
        float depositCosts = (amountMonth * percentBankMonth) / 100; // подсчёт ежемесячных платежей на депозит
        float depositPercent = (accumulation * percentBankMonth) / 100; // подсчёт ежемесячных платежей на первоначальный взнос
        float total = robot - accumulation; // подсчёт стоимости робота с учётом первоначального взноса
        int count = 0; // счётчик месяцев накоплений депозита

        //алгоритм расчёта депозита
        while (total > 0) {
            count++; // добавление нового месяца накопления
            total = total - amountMonth - depositPercent - depositCosts; //вычисление остатка накопления с учётом первоначального взноса, стипендии и процента
            // заполнение массива ежемесячных платежей  по депозиту
            if (total > amountMonth) { // если сумма остатка накопления больше ежемесячного платежа,то
                monthlyPayments[count + 1] = amountMonth; // в массиве добавляется  целый платёж
            } else { // иначе
                monthlyPayments[count + 1] = total; // в массиве добавляется платёж равный остатку накоплений
            }
        }
            return count;
        }

    }