# TCHALAM

## Table of Contents
1. [Overview](#Overview)
1. [Product Spec](#Product-Spec)
1. [Wireframes](#Wireframes)
1. [Schema](#Schema)

## Overview
### Description
TCHALAM is a preparation application for graduating students to better address university admission exams (Public, Private).

### App Evaluation
- **Category:** Educatif
- **Mobile:** This application is mainly developed for mobiles in order to properly address the exams for university admission
- **Story:** TCHALAM is an online prefact application where all graduating students can prepare quizzes for the university admission exam.
- **Market:** All graduating students who intend to integrate public and private universities can use the application
- **Habit:** This application can be used as often or rarely as the user wishes depending on subject to be prepared.
- **Scope:** The Tchalam application has an educational scope since it is a tool for graduating students  to integrate universities.

## Product Spec

### 1. User Stories (Required and Optional)

**Required Must-have Stories**
* User can Register a new Account
* User can Login
* The user can choose a subject to prepare.
* The user can take quizzes for a subject
* Profile


**Optional Nice-to-have Stories**

* Advanced Quiz
* Score Sharing

### 2. Screen Archetypes

* Login
   * User can login
* Register
   * User can register a new account
* Stream
    * user can see the list of exam subjects
* Choose Subject
    * User can choose a exam subject
* Quiz
    * User can answer quizzes for an exam subject
* Profile
    * User can see their progress on an exam topic

### 3. Navigation

**Tab Navigation** (Tab to Screen)

* Stream
* Profile
* Quiz

**Flow Navigation** (Screen to Screen)

* Login
   * Stream
* Register
   * Stream
* Stream
    * Detail
* Choose Subject
    * Stream
* Quiz
    * Quiz
* Profile
    * Detail

## Wireframes
<img src="https://i.imgur.com/cIVfGcF.jpg" width=600>

### [BONUS] Digital Wireframes & Mockups

### [BONUS] Interactive Prototype

## Schema 

### Models
Model: User

| Property  | Type     | Description                            |
| --------- | -------- | -------------------------------------- |
| ObjectId  | String   | Unique id for User account             |
| createdAt | DateTime | date created user (default field)      |
| updatedAt | DateTime | date last updated user (default field) |
| lastname  | String   | name of the user                       |
| firstname | String   | first name of the user                 |
| email     | String   | email of the user                      |
| password  | String   | Password of the user                   |

Model: Subject

| Property  | Type     | Description                           |
| --------  | -------- | --------------------------            |
| ObjectId  | String   | Unique id for subject 
 Name  | String   | Name of the subject
|
|
| subject   | String   | Subject 							   | 


Model: Quiz

| Property  | Type     | Description                           |
| --------  | -------- | --------------------------            |
| ObjectId  | String   | Unique id for Quiz            |
| createdAt | DateTime | date created quiz (default field)     |
| updatedAt	| DateTime | date last updated quiz (default field)|
| quiz      | String   | Question about a subject              |
| subject | Pointer  | Pointer to Subject                    |

Model: Answer

| Property  | Type     | Description                           |
| --------  | -------- | --------------------------            |
| ObjectId  | String   | Unique id for Answer            |
| createdAt | DateTime | date created answer (default field)     |
| updatedAt | DateTime | date last updated anser (default field)|
| quiz	| Pointer  | Pointer to Quiz					   |
| user      | Pointer  | Pointer to User 					   |

### Networking
- [Add list of network requests by screen ]
- [Create basic snippets for each Parse network request]
- [OPTIONAL: List endpoints if using existing API such as Yelp]
