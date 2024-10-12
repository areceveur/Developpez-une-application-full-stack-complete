describe('Logout spec', () => {
    it('Should login successfully', () => {
        cy.visit('/auth/login')
        cy.get('input[formControlName=email]').type("new@user.com");
        cy.get('input[formControlName=password]').type("NewUser@1234");
        cy.get('button[type="submit"]').click();
    });

    it('Should logout successfully', () => {
        cy.intercept('GET', '/api/auth/me', {
            statusCode: 200,
            body: {
                id: 1,
                username: 'NouveauUser',
                email: 'new@user.com'
            }
        }).as('getUser');
        cy.wait('@getUser');

        cy.visit('auth/me');
        cy.get('.logout-button').click();
    })
})