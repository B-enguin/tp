---
  layout: default.md
  title: "Lim Zhong Han's Project Portfolio Page"
---

### Project: HomeBoss

HomeBoss is a desktop application used for managing deliveries for home business owners.
The user interacts with it using a CLI, and it has a GUI created with JavaFX.
It is written in Java, and has about 19 kLoC.

Given below are my contributions to the project.

* **New Feature:** Added the user login and logout commands.
  * What it does: This feature allows the user to login and logout of the application.
  * Justification: This feature is important because it allows the user to secure their data
    and prevent unlawful access to the data through the GUI.
  * Highlights: Since this feature is based on a new entity, it required the creation of the `User` model, which
    stores the various fields needed for the user commands. As the user should not be able to execute most of the
    commands without logging in, the blocking of the execution of the commands was also implemented.
  * Credits: The implementation of the `User` model was adapted from the AB3 `Person` model.
  * Related pull requests: [#115](https://github.com/AY2324S1-CS2103T-T13-3/tp/pull/115)
    [#124](https://github.com/AY2324S1-CS2103T-T13-3/tp/pull/124) 
    [#139](https://github.com/AY2324S1-CS2103T-T13-3/tp/pull/139)

* **New Feature:** Added the user update commands.
  * What it does: This feature allows the user to update their account details.
  * Justification: This feature is important because it allows the user to update their details to secure their data.
  * Related pull requests: [#201](https://github.com/AY2324S1-CS2103T-T13-3/tp/pull/201)

* **Major Enhancement:** Added Customer ID field to the Customer model.
  * What it does: This enhancement allows the user to identify customers by their ID instead of the index of the 
    displayed UI list.
  * Justification: This enhancement allows the user to identify customers by their ID, which will be stored
    in the delivery linked to the customer.
  * Highlights: This enhancement required the creation of the class-level variable `customerCount` in the
    `Customer` model, which complicates the testing of the model as the `customerId` created does not match the
    expected `customerId` during the early stage of implementation.
  * Related pull requests: [#110](https://github.com/AY2324S1-CS2103T-T13-3/tp/pull/110)

* **Enhancements to existing features:**
  * Added JUnit tests for all features implemented by me.
  * Bug fixing in V1.3. Related pull requests: [442](https://github.com/AY2324S1-CS2103T-T13-3/tp/pull/442)

* **Code contributed:** [RepoSense link](https://nus-cs2103-ay2324s1.github.io/tp-dashboard/?search=zhonghan721&breakdown=true)

* **Contributions to team-based tasks:**
  * Managed release of `v1.3.trial` on GitHub
  * Added the sections About this User Guide and Navigating the User Guide in UserGuide.md
    (Pull request [#298](https://github.com/AY2324S1-CS2103T-T13-3/tp/pull/298))
  * Added value proposition of HomeBoss in DeveloperGuide.md
    (Pull request [#71](https://github.com/AY2324S1-CS2103T-T13-3/tp/pull/71))
  * Updated the details of Acknowledgements, NFR, Glossary and Effort in DeveloperGuide.md
    (Pull requests [#455](https://github.com/AY2324S1-CS2103T-T13-3/tp/pull/455)
    [#457](https://github.com/AY2324S1-CS2103T-T13-3/tp/pull/457))

* **Documentation:**
  * User Guide:
    * Added/Updated documentation for the features `login`, `logout`, `update`, `help`, `exit`, `clear`, `customer add`,
      `customer find` (Pull requests [#298](https://github.com/AY2324S1-CS2103T-T13-3/tp/pull/298)
      [#315](https://github.com/AY2324S1-CS2103T-T13-3/tp/pull/315))
    * Added the sections About this User Guide and Navigating the User Guide
      (Pull request [#298](https://github.com/AY2324S1-CS2103T-T13-3/tp/pull/298))
  * Developer Guide:
    * Added value proposition of HomeBoss (Pull request [#71](https://github.com/AY2324S1-CS2103T-T13-3/tp/pull/71))
    * Added implementation details of the features `login`, `logout`, `customer add`
      (Pull requests [#177](https://github.com/AY2324S1-CS2103T-T13-3/tp/pull/177)
      [#191](https://github.com/AY2324S1-CS2103T-T13-3/tp/pull/191))
    * Updated the details of Acknowledgements, NFR, Glossary and Effort
      (Pull requests [#455](https://github.com/AY2324S1-CS2103T-T13-3/tp/pull/455)
      [#457](https://github.com/AY2324S1-CS2103T-T13-3/tp/pull/457))

* **Community:**
  * PRs reviewed: As of 9th November, I have reviewed 32 PRs
    [(GitHub)](https://github.com/AY2324S1-CS2103T-T13-3/tp/pulls?q=is%3Apr+reviewed-by%3Azhonghan721+is%3Aclosed)
  * Reported bugs for other teams during PE Dry Run:
    reported 12 bugs posted as [issues](https://github.com/zhonghan721/ped/issues)
