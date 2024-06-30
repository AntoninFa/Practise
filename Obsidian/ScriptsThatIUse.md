My daily notes are created using the core Daily Plugin. To ensure all scripts function properly, you'll also need the Dataview, Task, and Templater plugins.

# Properties
Each daily note begins with properties, which are part of the Dataview-Plugin. I use these properties to track various metrics, such as the number of hours I slept and my daily expenditures. These notes can then be analyzed with queries, such as:

```dataview
TABLE 
  round(sum(number(rows.sleep))/length(rows.sleep), 2) as "Avg. Sleep"
FROM "daily"
WHERE sleep > 0
  AND date(file.day) >= date("2024-01-01")
FLATTEN date(file.day).weekyear AS Week
GROUP By Week as "Kalenderwoche"
```
## What I Spent in May of 2024, Differentiating Between Necessary and Unnecessary Expenses

```dataviewjs
let pages = dv.pages('"daily"').where(p => p.file.name.includes("2024") && p.file.name.includes("-05-"))
let expenses = 0;
   for(let i = 0; i < pages.length; i++) {
     if(pages[i].exp) {
       expenses += Number(pages[i].exp);
     }
   }
let fun = 0;
   for(let i = 0; i < pages.length; i++) {
     if(pages[i].fun) {
       fun += Number(pages[i].fun);
     }
   }
dv.paragraph("Nötige Ausgaben: " + expenses + "€")
dv.paragraph("Fun Ausgaben: " + fun + "€")
dv.paragraph("=> " + Number(fun + expenses) + "€")
```
# Tasks
Allows me to schedule tasks for specific days (daily notes), set due dates and priorities, and list tasks completed on a given day:

```tasks
(((due before tomorrow) OR (scheduled before tomorrow)) AND (not done)) OR (due today)
(path includes daily) OR (tags include #daily)
```
```dataviewjs
	dv.taskList(dv.pages().file.tasks 
	  .where(t => t.completed)
	  .where(t => t.text.includes(dv.current().file.name)))
```
```dataviewjs
	dv.taskList(dv.pages().file.tasks 
	  .where(t => !t.completed)
	  .where(t => t.text.includes(dv.current().file.name)))
```
# Templater
I use this plugin to generate a note for each daily entry. The corresponding note is placed in an encrypted folder, making it suitable for more private entries, for example something you'd write in a diary.
---
<%* const folderPath = "daily/day";
let dayLink = `${folderPath}/${tp.date.now("YYYY-MM-DD")}`;
%>
DAY: [[<% dayLink %>]]
---
# Birtdays
A small DataviewJS script that lists the names of all persons who have birthdays in the same month as the corresponding daily note.

```dataviewjs
let pages = dv.pages('"notes/Doku/Menschen"').where(p => {
    // Extract the year and month from dv.current().file.name
    let currentDate = new Date(dv.current().file.name);
    let currentMonth = currentDate.getMonth() + 1; // Adding 1 because getMonth() returns zero-based month index
    
    // Extract the year and month from p.birthday
    let birthday = new Date(p.birthday);
    let birthdayMonth = birthday.getMonth() + 1; // Adding 1 because getMonth() returns zero-based month index
    
    // Compare if the month of dv.current().file.name matches the month of p.birthday
    return currentMonth === birthdayMonth;
});

// Output for all fitting pages
pages.forEach(page => {
// Converting the birthday field of a page to a date
    let birthday = new Date(page.birthday);
    
    dv.paragraph(page.file.name + ", " + birthday.toDateString());
});
```
