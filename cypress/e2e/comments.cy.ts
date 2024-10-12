describe('Article comment section', () => {

    it('Should add a comment to an article', () => {
        cy.visit('/auth/login');
        cy.get('input[formControlName="email"]').type("new@user.com");
        cy.get('input[formControlName="password"]').type("NewUser@1234");
        cy.get('button[type="submit"]').click();

        cy.url().should('include', '/articles');

        cy.contains('mat-card', "Test").within(() => {
            cy.contains('button', 'Voir plus').click();
        });

        cy.get('.comment-section').should('be.visible');
        cy.get('textarea[formControlName="content"]').type('Ceci est un commentaire de test.');
        cy.get('button[type="submit"]').click();
        cy.get('.comment-container').should('contain', 'Ceci est un commentaire de test');
    });
});
