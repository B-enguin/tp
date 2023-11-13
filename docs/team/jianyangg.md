---
layout: default.md
title: "Lim Jian Yang's Project Portfolio Page"
---

### Project: HomeBoss

#### Overview

HomeBoss is a desktop application for **home-based business owners** to **manage their Customers and Deliveries**.
HomeBoss streamlines the process of your operations, optimises your resources, and enhances your overall business
experience.

#### Summary of Contributions

Given below are my contributions to the project.

* **New Feature:** Added ability to register for an account in HomeBoss
    * What it does: Allows the user to register for an account in HomeBoss. Upon registering for an account, the user
      will have their user details stored in a file, with the password hashed for enhanced security.
    * Justification: This feature improves the product as it allows the user to secure their data and prevent unlawful
      access to the data through the GUI.
    * Highlights: The password is hashed using SHA-256 hashing algorithm, which is a one-way hashing algorithm. This
      ensures that the password cannot be retrieved from the hash, and the password is stored securely. As the user
      should not be able to execute most of the commands without logging in (and by
      extension, having an account registered), the blocking of
      the execution of commands that deal with the Customer and Delivery database was also implemented.
    * Related Pull Requests:
        * [\#124](https://github.com/AY2324S1-CS2103T-T13-3/tp/pull/124)
        * [\#194](https://github.com/AY2324S1-CS2103T-T13-3/tp/pull/194)
        * [\#225](https://github.com/AY2324S1-CS2103T-T13-3/tp/pull/225)

* **New Feature**: Added ability to recover an account in HomeBoss
    * What it does: Allows the user to recover their account in HomeBoss in case they forget their password.
    * Justification: This feature allows for corrective action to be taken in case the user forgets their password. This
      in turn ensures that they are able to access their data through the GUI if they forget their password.
    * Highlights:
    * Related Pull Requests:

* #### Code contributed:
    * [RepoSense link](https://nus-cs2103-ay2324s1.github.io/tp-dashboard/?search=jianyangg&sort=groupTitle&sortWithin=title&timeframe=commit&mergegroup=&groupSelect=groupByRepos&breakdown=true&checkedFileTypes=docs~functional-code~test-code)

* ##### Enhancements Implemented
    * Register account feature:
    * Recover account feature:
    * Delete account feature:
    * Customer delete feature:
    * Test cases (for all implemented features):

- ##### Contributions to the User Guide
  *to be added soon*

- ##### Contributions to the Developer Guide
    * Use cases
    * Glossary
    * Class diagram for UI component
    * Sequence and activity diagrams for `RegisterAccountCommand`

- ##### Contributions to team-based tasks
  *to be added soon*

- ##### Review/mentoring contributions
    * Created issues, reviewed, approved and merged pull requests.
    * Left comments on PRs to suggest improvements.

- ##### Contributions beyond the project team
    * Reported bugs during PE dry-run
