Feature: As a registered user can log in with valid username/password-combination

    Scenario: nonexistent user can not login to
        Given login is selected
        When username "matti" and password "ittam" are given
        Then user is not logged in and error message is given

