describe('Logout spec', () => {
    it('Should login successfully', () => {
        cy.visit('/auth/login')
        cy.get('input[formControlName=email]').type("nouveau@user.com");
        cy.get('input[formControlName=password]').type("NouveauPassword@1234");
        cy.get('button[type="submit"]').click();
    });

    it('Should logout successfully', () => {
        cy.visit('auth/me');
        cy.get('button').contains('Se déconnecter').click();
    })
})