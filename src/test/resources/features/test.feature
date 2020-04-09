Feature: Test Employee
  这是test
  @tag111111
  Scenario: WITH ALL REQUIRED FIELDS IS SUCCESSFUL
    这是描述
    Given when a customer wanna create a user with the following attributes
      | id  | firstName | lastName | dateOfBirth | startDate  | employmentType | email               |
      | 100 | Rachel    | Green    | 1990-01-01  | 2018-01-01 | Permanent      | rachel.green@fs.com |

    And with the following phone numbers
      | id  | type   | isdCode | phoneNumber | extension |
      | 102 | Mobile | +1      | 2141112222  |           |
      | 103 | Office | +1      | 8362223000  | 333       |

    When save 'WITH ALL REQUIRED FIELDS'
    Then the save 'OK'
  Scenario: test3
    Given i'm given
    But 22222
    When i'm when
    Given 44444
    * btttt2
    Then i'm 5555
