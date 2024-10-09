describe('Login spec', () => {
  it('Login successfully', () => {
    cy.visit('/auth/login');
    cy.get('input[formControlName=email]').type("user@test.com");
    cy.get('input[formControlName=password]').type(`${"UserTest@1234"}`);
    cy.get('button[type="submit"]').click();
    cy.url().should('include', '/articles');
  });

  it('Displays error on incorrect login/password', () => {
    cy.visit('/auth/login');

    cy.get('input[formControlName=email]').type("user@test.com");
    cy.get('input[formControlName=password]').type("userTest!1234");
    cy.get('button[type="submit"]').click();

    cy.get('.error').should('be.visible').and('contain', 'An error occurred');
  });

  it('Disable submit button if required fields are missing', () => {
    cy.visit('/auth/login');
    cy.get('input[formControlName=email]').clear();
    cy.get('input[formControlName=password]').clear();

    cy.get('button[type=submit]').should('be.disabled');
  })
})
