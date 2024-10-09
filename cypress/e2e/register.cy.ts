describe('Register Spec', () => {
    it('Register successfully', () => {
        cy.visit('/auth/register');
        cy.get('input[FormControlName=username]').type("NouvelUtilisateur");
        cy.get('input[FormControlName=email]').type("nouveau@user.com");
        cy.get('input[FormControlName=password]').type("NouveauPassword@1234");
        cy.get('button[type=submit]').click();
        cy.url().should('include', '/articles');
    });

    it('Displays error on incorrect password', () => {
        cy.visit('/auth/register');
        cy.get('input[FormControlName=username]').type("NouvelUtilisateur");
        cy.get('input[FormControlName=email]').type("nouveau@user.com");
        cy.get('input[FormControlName=password]').type("NouveauPassword");

        cy.get('button[type=submit]').should('be.disabled');
    })
})