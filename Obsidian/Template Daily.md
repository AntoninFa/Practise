---
tags:
  daily
consumables:
  protein:
sleep:
exp:
fun:
---
# Tasks
```tasks
(((due before tomorrow) OR (scheduled before tomorrow)) AND (not done)) OR (due today)
(path includes daily) OR (tags include #daily)
```
## >

# Notes


<%* const folderPath = "daily/day";
let dayLink = `${folderPath}/${tp.date.now("YYYY-MM-DD")}`;
%>
DAY: [[<% dayLink %>]]
Yesterday: [[<% tp.date.now("YYYY-MM-DD", -1) %>]]
Tomorrow: [[<% tp.date.now("YYYY-MM-DD", 1) %>]]
## [[notes/0Main Kanban|0Main Kanban]]
## Geburtstage
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
# Done
```dataviewjs
	dv.taskList(dv.pages().file.tasks 
	  .where(t => t.completed)
	  .where(t => t.text.includes(dv.current().file.name)))
```
[[notes/Orga/0AllTasks|0AllTasks]]
# An dem Tag
```dataviewjs
	dv.taskList(dv.pages().file.tasks 
	  .where(t => !t.completed)
	  .where(t => t.text.includes(dv.current().file.name)))
```
Note generated at: <% tp.date.now("YYYY-MM-DD HH:mm:ss") %>