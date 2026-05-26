# Search Functionality Test Cases — tmsandbox.co.nz

## Functional — Happy Path

## Search

| TestId | Test Case                                                  | Suite       |
|--------|------------------------------------------------------------|-------------|
| TC22   | Search box is visible on homepage                          | Smoke       |
| TC23   | Search box has correct placeholder text                    | Smoke       |
| TC24   | Valid keyword search returns relevant results              | Smoke       |
| TC25   | Search triggered by clicking Search button                 | Sanity      |
| TC26   | Search triggered by pressing Enter key                     | Sanity      |
| TC27   | Empty search does not crash and handles gracefully         | Regression  |
| TC28   | Search with only spaces handles gracefully                 | Regression  |
| TC29   | Search with special characters does not return results     | Regression  |
| TC30   | Search with numbers returns results                        | Regression  |
| TC31   | Search with very long string does not crash                | Regression  |
| TC32   | Search with single character may or may not return results | Regression  |
| TC33   | Search is case insensitive                                 | Regression  |
| TC34   | Search with no results shows appropriate message           | Regression  |
| TC35   | Search keyword retained on results page                    | Sanity      |
| TC36   | Results page title contains searched keyword               | Sanity      |