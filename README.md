# iGrad 
> A handy guide to help you make the most of your application

## Table of Contents

1. [Introduction](#Introduction)
2. [Features](#Features)
    
    2.1 [Course Builder](#course-builder)
    
    2.2 [Modular Credits Tracker](#modular-credits-mcs-tracker)
    
    2.3 [Grade Point Average Tracker](#grade-point-average-gpa-tracker)

3. [Components](#Components)

    3.1 [Course](#courses)
    
    3.2 [Requirements](#requirements)
    
    3.3 [Modules](#modules)
    
4. [Walkthrough](#walkthrough)
5. [Command List](#command-list)
    
    5.1.[`help`](#help)
    
    5.2 [`course`](#course)
    
    5.3 [`requirement`](#requirements)
    
    5.4 [`assign`](#assign)
    
    5.5 [`module`](#module)
  
    5.6 [`modify`](#modify)
  
    5.7 [`delete`](#delete)
  
    5.8 [`view`](#view)
  
    5.9 [`achieve`](#achieve)
  
    5.10 [`import`](#import)
  
    5.11 [`export`](#export)
  
    5.12 [`exit`](#exit)
   
6. Frequently Asked Questions (FAQ)
7. Cheat Sheet
8. Glossary

---

## Introduction

Sick of having tracking your university progress the manual way? 
Start getting rid of your Excel sheets and scribbled down notes and download *iGrad* right now!

What is *iGrad*? 

*iGrad* is the app to track your university progress, for students who are frustrated with the
limited features the university provides, by students who are frustrated by the limited
features the university provides. 

*iGrad* offers users the ability to create custom courses and graduation requirements,
ensuring the **maximum** degree of flexibility when planning and keeping track of your progress

*iGrad* also retrieves data directly from [NUS Mods](https://www.nusmods.com), ensuring that module
information is always up to date.

*iGrad* calculates your GPA at every step, ensuring you never have to use a GPA calculator again

Finally, the *iGrad* team is always open to feedback and suggestions from the public will always be followed up on.

## Features  

#### Course Builder
iGrad was built with every NUS student in mind. Our custom course builder allows you to build
the course of your dreams.

#### Modular Credits (MCs) Tracker
We are sick of counting our MCs at the beginning of every semester too. Easily see how many MCs you
have left in order to apply for graduation.

#### Grade Point Average (GPA) Tracker
No more googling GPA Calculators. iGrad's GPA tracker keeps track of your GPA at every step and
even offers predictive services so you know how well you have to do
in order to achieve your dream Cumulative Point Average (CAP).

## Components

![](https://user-images.githubusercontent.com/34233605/75425925-9774ff80-597e-11ea-87f5-228f95b5c84f.png)

#### Courses
A course is simply a group of requirements. It is also how we keep track of your overall GPA
and MCs. 

#### Requirements
A requirement consists of at least one module. Fulfill all modules within a requirement to
complete it.

#### Modules
A module is the building block of all other components. Mark your modules as done and give it
a grade. You can also add optional memos to help you remember why
you took the module.

## Walkthrough

#### 1. Start up the application

#### 2. Enter your course details

#### 3. Key in your graduation requirements

#### 4. Assign your modules

#### 5. Mark a module as done

#### 6. Key in a memo

#### 7. Track your MCs 

#### 8. View your GPA

#### 9. Import external data

#### 10. Export your data

## Command List

#### `help`

Displays a help message to the user. Lists all possible commands
and provides a link to the user guide online.

Command Format

    help

Constraints
   
:warning: NIL
   
Expected Outcome

:white_check_mark: A help message should be displayed

---

#### `course`

Creates a course.

Command Format

    course n/COURSE_NAME
    
    /*
    * Creating a course named "Computer Science"
    */
    course n/Computer Science
    

Constraints
   
:warning: You can only have one course at a time
   
Expected Outcome

:white_check_mark: You should be able to see the course name in the 
top panel

---
    
#### `requirement`

Creates a graduation requirement.

Command Format

    requirement n/REQUIREMENT_NAME u/NO_OF_MCS
    
    /**
    * Creating a requirement named "Unrestricted Electives" 
    * which requires 32 MCs to fulfill
    */
    requirement n/Unrestricted Electives u/32

Constraints
   
:warning: The number of MCs needed to fulfill the requirement is needed

:warning: Requirement names have to be unique 
   
Expected Outcome

:white_check_mark: You should be able to see the requirement name in the
main panel

---

#### `assign`

Assigns a module to a graduation requirement. If there is good internet
connectivity, the module will be validated with NUS Mods and its description
will be auto-filled.

Command Format
    
    /**
    * DESCRIPTION is optional
    */
    assign n/REQUIREMENT_NAME: n/MODULE_CODE t/MODULE_TITLE u/NO_OF_MCS d/DESCRIPTION
    
    /**
    * Assigns module "LAJ1201 Japanese 1" worth 4 MCs 
    * to requirement "Unrestricted Electives"
    */
    assign n/Unrestricted Electives: n/LAJ1201 t/Japanese 1 u/4

Constraints
 
:warning: A module cannot be assigned if there are not enough MCs left under 
a graduation requirement

:warning: The module code and title have to be unique
   
Expected Outcome

:white_check_mark: The module will be displayed in the main panel

---

#### `module`

Tags a module with semester, grade or memo notes.

Command Format
    
    /**
    * At least one option must be specified.
    * SEMESTER is specified in format Y_S_ ( e.g. Y1S2 - Year 1 Semester 2 ) 
    */
    module n/MODULE_CODE: s/SEMESTER g/GRADE m/MEMO_NOTES
    
    /**
    * Tags CS1101 with "Y1S2" and grade "A+"
    */
    module n/CS1101: s/Y1S2 g/A+
    
    /**
    * Tags ST2234 with "Y2S1" and gives it a memo "pretty easy module"
    */
    module n/ST2334: s/Y2S1 m/pretty easy module

Constraints
   
:warning: The module has to be assigned
   
Expected Outcome

:white_check_mark: The tags should appear under their respective column headers

    
#### `modify`

Modify course, graduation requirements or modules

Command Format

    modify course n/COURSE_NAME
    
    modify req n/REQUIREMENT_NAME: u/NO_OF_MCS
    
    /**
    * At least one option must be specified
    */
    modify module n/MODULE_CODE: t/MODULE_NAME u/NO_OF_MCS

    /**
    * Modifies CS4239 and gives it the title "Machine Learning" and updates
    * it to be worth 5 MCs
    */
    modify module n/CS4239: t/Machine Learning u/5

Constraints
   
:warning: The component to be modified must exist in the system

:warning: The COURSE_NAME, REQUIREMENT_NAME, MODULE_NAME and MODULE_CODE
have to be unique
   
Expected Outcome

:white_check_mark: You should be able to see the relevant changes
    
#### `delete`

Deletes course, graduation requirements or modules

Command Format

    del course n/COURSE_NAME
    
    del req n/REQUIREMENT_NAME
    
    del module n/MODULE_CODE
    
    /**
    * Deletes the course "Computer Science"
    */
    del course n/Computer Science

Constraints
   
:warning: The component to be deleted must exist in the system
   
Expected Outcome

:white_check_mark: The components deleted should disappear from their respective panels

    
#### `exam`

View your examination results.

Command Format

    /**
    * SEMESTER is optional.
    * If not specified, displays results for all semesters.
    */
    exam s/SEMESTER
    
    /**
    * Displays exam results for Year 3 Semester 2 
    */
    exam s/Y3S2

Constraints

:warning: NIL
   
Expected Outcome

:white_check_mark: You should be able to view your exam results
    
#### `achieve`

Calculates the average grade needed to achieve the CAP you desire/

Command Format

    achieve c/DESIRED_CAP

    /**
    * Calculates the avergae grade needed
    * to achieve a CAP of 4.50
    */
    achieve c/4.50

Constraints
   
:warning: NIL
   
Expected Outcome

:white_check_mark: You should be able to view the average grade needed to achieve
the CAP you desire
    
#### `import`

Executes a series of commands written in a text (.txt) file

Command Format

    import f/ FILE_PATH

    import f/ C:\Users\wayne\OneDrive\Desktop\commands.txt

Constraints
   
:warning: The text file has to properly formatted. 
Partial execution will not take place.
   
Expected Outcome

:white_check_mark: All changes should be made based on the commands
in the text file. Please cross-check changes with the file to ensure 
this is so.
    
#### `export`

Displays a help message to the user. Lists all possible commands
and provides a link to the user guide online.

Command Format

    help

Constraints
   
    NIL
   
Expected Outcome

    1. A help message should be displayed as a pop-up
    
#### `exit`

Displays a help message to the user. Lists all possible commands
and provides a link to the user guide online.

Command Format

    help

Constraints
   
    NIL
   
Expected Outcome

    1. A help message should be displayed as a pop-up
    
