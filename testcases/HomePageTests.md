# Homepage Test Cases — tmsandbox.co.nz

## Page Load & Display

| TestId | Test Case                                               | Suite       |
|--------|---------------------------------------------------------|-------------|
| TC01   | Homepage loads successfully with correct title          | Smoke       |
| TC02   | Trade Me logo is displayed on homepage                  | Smoke       | 
| TC03   | Homepage displays correctly on desktop screen           | Regression  |
| TC04   | Homepage displays correctly on tablet screen            | Regression  |
| TC05   | Homepage displays correctly on mobile screen            | Regression  |
| TC06   | Homepage displays correctly on Chrome                   | Regression  |
| TC07   | Homepage displays correctly on Firefox                  | Regression  |
| TC08   | Homepage displays correctly on Safari                   | Regression  |
| TC09   | Homepage is served over HTTPS                           | Smoke       |

## Navigation & Header

| TestId | Test Case                                               | Suite      |
|--------|---------------------------------------------------------|------------|
| TC10   | Trade Me logo click returns user to homepage            | Sanity     |
| TC11   | Categories dropdown opens on click                      | Smoke      |
| TC12   | Categories dropdown displays all main categories        | Sanity     |
| TC13   | Clicking Marketplace navigates to correct page          | Sanity     |
| TC14   | Clicking Property navigates to correct page             | Sanity     |
| TC15   | Clicking Motors navigates to correct page               | Sanity     |
| TC16   | Clicking Jobs navigates to correct page                 | Sanity     |
| TC17   | Clicking Services navigates to correct page             | Sanity     |
| TC18   | Clicking Browse All navigates to correct page           | Sanity     |
| TC19   | Watchlist link redirects unauthenticated user to login  | Regression |
| TC20   | Favourites link redirects unauthenticated user to login | Regression |
| TC21   | Start a Listing redirects unauthenticated user to login | Regression |

## Search

| TestId | Test Case                                                  | Suite       |
|--------|------------------------------------------------------------|-------------|
| TC22   | Search box is visible on homepage                          | Smoke       |
| TC23   | Search box has correct placeholder text                    | Smoke       |
| TC24   | Search button is visible on homepage                       | Smoke       |

## Authentication

| TestId | Test Case                                                | Suite      |
|--------|----------------------------------------------------------|------------|
| TC25   | Login link opens login page                              | Smoke      |
| TC26   | Sign Up link navigates to registration page              | Smoke      |
| TC27   | Valid credentials allow successful login                 | Sanity     |
| TC28   | Invalid credentials show appropriate error message       | Regression |
| TC29   | Logged in user sees account name instead of Login button | Regression |
| TC30   | Logged out user sees Login button                        | Regression |

## Popular Searches & Listings

| TestId | Test Case                                                   | Suite      |
|--------|-------------------------------------------------------------|------------|
| TC31   | Popular search links displayed under correct categories     | Sanity     |
| TC32   | Clicking popular search link navigates to correct results   | Regression |
| TC33   | Featured listings section loads and displays items          | Sanity     |
| TC34   | Clicking featured listing navigates to correct listing page | Regression |

## Footer

| TestId | Test Case                                             | Suite      |
|--------|-------------------------------------------------------|------------|
| TC35   | Footer displays all expected links                    | Sanity     |
| TC36   | Desktop site link navigates to correct page           | Regression |
| TC37   | About us link navigates to correct page               | Regression |
| TC38   | Careers link navigates to correct page                | Regression |
| TC39   | News link navigates to correct page                   | Regression |
| TC40   | Advertise link navigates to correct page              | Regression |
| TC41   | Privacy policy link navigates to correct page         | Regression |
| TC42   | Terms & conditions link navigates to correct page     | Regression |
| TC43   | Contact Us link navigates to correct page             | Regression |
| TC44   | Social media links open correct platforms             | Regression |
| TC45   | Partner site links navigate to correct external sites | Regression |

## Security & Accessibility

| TestId | Test Case                                      | Suite         |
|--------|------------------------------------------------|---------------|
| TC46   | No sensitive data exposed in page source       | Security      |
| TC47   | Homepage is accessible via keyboard navigation | Accessibility |
| TC48   | All images have alt text                       | Accessibility |

## Performance

| TestId | Test Case                                               | Suite       |
|--------|---------------------------------------------------------|-------------|
| TC49   | Homepage loads within acceptable time (under 3 seconds) | Performance |
| TC51   | Search results load within acceptable time              | Performance |
| TC52   | Page handles slow network gracefully                    | Performance |

## Reason For Test Suite Mapping

### Smoke
Core search working — run after every deployment.

### Sanity
Core search features intact — run after search related changes.

### Regression
Full search coverage — run before every release.

### Performance (run before major releases)
Load time and behaviour under stress.