describe('Article list and creation', () => {

    it('Should return the list of articles', () => {
        cy.visit('/auth/login');
        cy.get('input[formControlName="email"]').type("user@test.com");
        cy.get('input[formControlName="password"]').type("UserTest@1234");
        cy.get('button[type="submit"]').click();

        cy.url().should('include', '/articles')
        cy.get('.item').should('be.visible');
    });

    it('Should test the button create and fill the form', () => {
        cy.get('button[routerLink="create"]').click();

        cy.get('mat-select[formControlName="themeId"]').click();
        cy.get('mat-option').contains("Développement web").click();

        cy.get('input[formControlName=titre]').type('Titre de l\'article5');
        cy.get('textarea[formControlName=contenu]').type('Contenu de l\'article5');
        cy.get('button[type="submit"]').click();
    })

    it('Should display a detailed session', () => {
        cy.contains('mat-card', "Test").within(() => {
            cy.contains('button', 'Voir plus').click();
        });
    });

    it('Should go back to the session list', () => {
        cy.get("button").contains("Retour").click()
    })

    it('Should test the sort button', () => {
        cy.get('button').contains('Trier par ordre').click();
        cy.get('button').contains('Trier par ordre').click();
    })
})
