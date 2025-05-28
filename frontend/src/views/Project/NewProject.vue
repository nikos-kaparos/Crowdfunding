<template>
    <div class="container mt-5">
      <h2>Create New Project</h2>
      <form @submit.prevent="createProject">
        <div class="mb-3">
          <label for="title">Title:</label>
          <input type="text" v-model="project.title" id="title" class="form-control" required />
        </div>
        <div class="mb-3">
          <label for="description">Description:</label>
          <textarea v-model="project.description" id="description" class="form-control" rows="5" required></textarea>
        </div>
        <div class="mb-3">
          <label for="requiredFunding">Required Funding:</label>
          <input type="number" v-model="project.requiredFunding" id="requiredFunding" class="form-control" required />
        </div>
       <button type="submit" class="btn btn-primary">Create</button>
      </form>
    </div>
  </template>
  
  <script>
  export default {
    data() {
      return {
        project: {
          title: '',
          description: '',
          requiredFunding: null,
        },
        username: '',
      };
    },
    mounted() {
      this.username = localStorage.getItem('username'); // Ανάκτηση του username από το localStorage
    },
    methods: {
      async createProject() {
        const token = localStorage.getItem('accessToken'); // Ανάκτηση του JWT token από το localStorage
        try {
          const response = await fetch('http://localhost:8080/api/project/new', {
            method: 'POST',
            headers: {
              'Content-Type': 'application/json',
              Authorization: `Bearer ${token}`, // Προσθήκη του JWT token στο header
            },
            body: JSON.stringify(this.project), // Μετατροπή του project σε JSON
          });
  
          if (!response.ok) {
            throw new Error(`HTTP error! status: ${response.status}`);
          }
  
          const data = await response.json();
          console.log('Project created:', data);
          // Μεταφορά στη σελίδα με τα projects ή άλλη ενέργεια
          this.$router.push('/creator');
        } catch (error) {
          console.error('Error creating project:', error);
        }
      }
    },
  };
  </script>
  
  <style scoped>
  .container {
    max-width: 600px;
  }
  </style>
  