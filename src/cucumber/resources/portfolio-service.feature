Feature: Portfolio service

  Scenario: Portfolios can be persisted to and retrieved from the database:
    Given the database is empty
    And The following trades are executed:
      | dateTime                | price | buyAdvice | ticker | numOfSharesToBuy | maxMonthlyInvestment |
      | 1998-12-31T23:59:59.999 | 1.34  | 2.68      | intc   | 2                | 600                  |
      | 2000-01-01T00:00:00.000 | 1.34  | 2.68      | intc   | 2                | 600                  |
    When a request is made to retrieve all portfolios
    Then the following portfolios should be returned:
      | dateTime                | price | buyAdvice | ticker | numOfSharesToBuy | numOfSharesHeld | sharesHeldValue | cashHeldValue | accountValue | maxMonthlyInvestment |
      | 1998-12-31T23:59:59.999 | 1.34  | 2.68      | intc   | 2                | 2               | 2.68            | 597.32        | 600.00       | 600                  |
      | 2000-01-01T00:00:00.000 | 1.34  | 2.68      | intc   | 2                | 4               | 5.36            | 1194.64       | 1200.00      | 600                  |

