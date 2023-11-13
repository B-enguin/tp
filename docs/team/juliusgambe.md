---
layout: default.md
title: "Julius Gambe's Project Portfolio Page"
---

### Project: HomeBoss

HomeBoss is a desktop application used for managing deliveries for home business owners.
The user interacts with it using a CLI, and it has a GUI created with JavaFX.
It is written in Java, and has about 19 kLoC.

Given below are my contributions to the project.

** New Feature ** : Added the ability to list deliveries with filter and sort functionalities

* What it does: Allows the user to list deliveries with a filter and sort their deliveries
* Justification: This feature improves the product significantly because it allows the user to view their deliveries
  in a more organised manner.
* Highlights: This required two separate lists to be created, one for the filtered deliveries and one for the sorted
  deliveries. The filtered deliveries list is created by iterating through the original list and adding deliveries that
  match the filter criteria. The sorted deliveries list is created by sorting the filtered deliveries list using a
  comparator. The filtered deliveries list is then displayed to the user.
* Related Pull Requests: [\#158](https://github.com/AY2324S1-CS2103T-T13-3/tp/pull/158)
  , [#202](https://github.com/AY2324S1-CS2103T-T13-3/tp/pull/202)

** New Feature ** : Added Delivery View

* What it does: Allows the user to view a delivery in detail
* Justification: This feature improves the product significantly because it allows the user to view their deliveries
  with details.
* Highlights: This required a new class to be created to handle the view delivery command. The view delivery command
  takes in the id of the delivery to be viewed and displays the delivery details including the date to the user.
* Related Pull Requests: [\#158](https://github.com/AY2324S1-CS2103T-T13-3/tp/pull/158)
