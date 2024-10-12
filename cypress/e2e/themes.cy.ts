describe('Theme list', () => {

    it('Should visit the theme page', () => {
        cy.clearLocalStorage();
        cy.visit('/auth/login');
        cy.get('input[formControlName="email"]').type("new@user.com");
        cy.get('input[formControlName="password"]').type("NewUser@1234");
        cy.get('button[type="submit"]').click();
    });

    it('should display the list of themes', () => {
        cy.get(`[routerLink=themes]`).click();
    });

    it('should display the subscription correctly', () => {
        cy.contains('mat-card-content', 'Ensemble de règles et de syntaxes permettant d\'écrire des programmes qui seront exécutés par un ordinateur').within(() => {
            cy.contains('button', 'S\'abonner').click();
        })
    });

    it('Should display the unsubscription correctly', () => {
        cy.get(`.mat-icon`).click();

        cy.contains('mat-card-content', 'Ensemble de règles et de syntaxes permettant d\'écrire des programmes qui seront exécutés par un ordinateur').within(() => {
            cy.contains('button', 'Se désabonner').click();
        });
    })

})