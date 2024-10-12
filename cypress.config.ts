import { defineConfig } from 'cypress';

export default defineConfig({
  projectId: 'pcp5mq',
  e2e: {
    baseUrl: 'http://localhost:4200/',
    testIsolation: false,
    setupNodeEvents(on, config) {
      // implement node event listeners here
    },
  },
});
