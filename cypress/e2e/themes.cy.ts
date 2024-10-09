describe('Article list and creation', () => {

    it('Should return the list of themes', () => {
        cy.visit('/auth/login');
        cy.get('input[formControlName="email"]').type("user@test.com");
        cy.get('input[formControlName="password"]').type("UserTest@1234");
        cy.get('button[type="submit"]').click();
    });

    it('Should visit the themes list',() => {
        cy.visit('themes');
    })
})