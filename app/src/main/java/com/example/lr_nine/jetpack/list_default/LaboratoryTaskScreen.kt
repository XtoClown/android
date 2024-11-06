package com.example.lr_nine.jetpack.list_default

import android.app.Activity
import android.content.Intent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.toMutableStateList
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.lr_nine.MainActivity


@Composable
fun LaboratoryTaskScreen(modifier: Modifier = Modifier, context: Activity){

    Column ( modifier = modifier ){
        var checkedCount = rememberSaveable { mutableStateOf(0) }
        Text(
            modifier = Modifier.height(60.dp).fillMaxWidth(),
            text = "Tasks done: ${checkedCount.value}",
            textAlign = TextAlign.Center,
            fontSize = 50.sp
        )
        var list = rememberSaveable(saver = laboratoryTasksSaver) { getTasks().toMutableStateList() }
        LaboratoryTasksList(list = list, checkedCount = checkedCount, onCloseTask = { task -> list.remove(task) })
        Button(onClick = {
            val intent = Intent(context, MainActivity::class.java)
            context.startActivity(intent) },
            modifier = Modifier
                .fillMaxWidth()
                .height(60.dp)) {
            Text(text = "List with ViewModel", fontSize=30.sp)
        }
    }
}

private fun getTasks() = listOf(
    LaboratoryTask(1, "Laboratory Work №1", "-\tВстановити Android Studio\n" +
            "-\tСтворити проект на Kotlin з мінімальною версією Android 5.1 та шаблоном Empty Activity (якщо в списку присутні Empty Views Activity то їх)\n" +
            "-\tСтворити емулятор мобільного пристрою\n" +
            "-\tСтворити 2 Empty Activity (якщо в списку присутні Empty Views Activity то їх)\n" +
            "-\tЗапустити проект на емуляторі\n" +
            "-\tСтворити емулятор будь-якого іншого типу пристрою (не смартфону)\n" +
            "-\tЗапустити та змінити активність (показавши і запущену стару активність і нову)\n"),
    LaboratoryTask(2, "Laboratory Work №2", "-\tСтворити 7 основних кольорів в colors.xml (дуже бажано не відтінки одного а різних кольорів)\n" +
            "-\tЛокалізувати рядки українською та англійською\n" +
            "-\tСтворити рядок який буде показувати книжкову орієнтацію та альбомну орієнтацію відповідно\n" +
            "-\tЛокалізувати рядок за орієнтацією та мовами одночасно\n" +
            "-\tСтворити 2 масиви рядків\n" +
            "-\tСтворити меню з елементами\n" +
            "-\tСтворити вкладене меню\n"),
    LaboratoryTask(3, "Laboratory Work №3", "-\tСтворити 3 функції які будуть повертати різні типи даних\n" +
            "-\tПеревизначити одну з функцій активностей\n" +
            "-\tСтворити список елементів отримавши його з xml ресурсів (ЛР2)\n" +
            "-\tСтворити 2 data class мінімум з 4 полів та продемонструвати мінімум 2 методи data class\n" +
            "-\tСтворити додатково 2 звичайні класи, в одному з них створити 2 другорядні конструктори\n" +
            "-\tПродемонструвати використання циклу when мінімум з 4 case\n"),
    LaboratoryTask(4, "Laboratory Work №4", "-\tМодифікувати створене меню, додати вибір фонового кольору активності за допомогою розміщення item всередині меню (використати всі наявні кольори які були створені)\n" +
            "-\tЗробити обробники для зміни фонового кольору \n" +
            "-\tСтворити контекстне меню в якому будуть змінюватись кольори фону TextView за допомогою передачі обраного кольору в активність з меню та за допомогою методу TextView з назвою setTextColor, зробити для всіх кольорів\n" +
            "-\tСтворити діалогове вікно для підтверждення переходу з однієї активності на іншу (якщо тема застосунку це інтернет магазин то потрібне підтверждення переходу з вікна покупки на вікно з успішною покупку)\n" +
            "-\tСтворити Layout кожного типу \n" +
            "-\tЯкщо в застосунку є якийсь статичний список, реалізувати його за допомогою TableLayout (якщо елементи в ньому не потребують обробки)\n" +
            "-\tСтворіть кілька (від 2 layout) та зробіть їх дочірними до якогось з основних layout за допомогою include\n"),
    LaboratoryTask(5, "Laboratory Work №5", "-\tОбробити за допомогою Toast або Logcat кожну функцію життєвого циклу активності\n" +
            "-\tВсередині основної активності потрібно створити 2 фрагменти, які не будуть займати всю активність, в активності мають бути 2 кнопки для відкривання фрагментів\n" +
            "-\tВ активності мають бути TextView для отримання назви фрагменту за допомогою прослуховувача фрагментів (в один з фрагментів можна додати список картинок як товарів, в інший список категорії, в третій улюблені товари та в 4 додати список придбаних товарів)\n" +
            "-\tСтворити в одному з фрагментів кнопку, яка буде відкривати іншу з створених активностей, всередині активності створити кнопку для повертання назад\n"),
    LaboratoryTask(6, "Laboratory Work №6", "-\tРеалізувати списки (RecyclerView(горизонтальний або вертикальний), ListView)\n" +
            "-\tРеалізувати власний адаптер з можливістю вибору елементів та обробниками натискань на кнопок (приклад шаблону – checkbox, картинка, button)\n" +
            "-\tРеалізувати ScrollView для вікон які можуть не вміщатись на маленькі екрани\n" +
            "-\tСтворити випадаючий список (spinner)\n"),
    LaboratoryTask(7, "Laboratory Work №7", "-\tСтворити базу даних з 2 таблицями які можна пов’язати один з одним\n" +
            "-\tРеалізувати всі стандартні CRUD методи Room для кожної з таблиць\n" +
            "-\tСтворити Query для отримання об’єднаних даних\n" +
            "-\tСтворити методи для отримання частини даних з двох таблиць (можна не одночасно)\n" +
            "-\tПродемонструвати отримання всіх даних з двох таблиць за допомогою їхніх класів\n"),
    LaboratoryTask(8, "Laboratory Work №8", "-\tСтворити firebase проєкт на console.firebase.google.com\n" +
            "-\tПід’єднати застосунок до Firebase\n" +
            "-\tДодати можливість авторизації за допомогою власних даних\n" +
            "-\tПід’єднати Crashlytics\n" +
            "-\tПоказати приклад події в CrashLytics\n" +
            "-\tВикористати FireStore або Realtime, навести основні операції CRUD\n" +
            "-\tВикористати RemoteConfig для зміни теми застосунку\n" +
            "-\tСтворити та використати будь-які 5 інших конфігурацій\n"),
    LaboratoryTask(9, "Laboratory Work №9", "-\tСтворити Composable Activity зі списком Taskів\n" +
            "-\tУ кожного завдання мають бути: деталі (які відкриваються з анімацією), checkbox зі статусом виконання та видалення з анімацією\n" +
            "-\tУ кожного завдання має зберігатись стан, а список елементів має зберігати свій стан (щоб видалені елементи не відображались при повторному показі)\n" +
            "-\tТакож потрібно додати лічильник виконаних завданнь\n" +
            "-\tПотрібно продемонструвати цей список як з використанням ViewModel так і без, при цьому в якості прикладу ререндеру потрібно повертати екран\n")
)