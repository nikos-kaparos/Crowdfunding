<template>
    <div class="container mt-5">
      <h1>Edit Project</h1>
      <div v-if="loading" class="alert alert-info">Loading project...</div>
      <div v-if="error" class="alert alert-danger">{{ error }}</div>
      <form v-if="project" @submit.prevent="updateProject">
        <div class="form-group">
          <label for="title">Title</label>
          <input type="text" class="form-control" id="title" v-model="project.title" required />
        </div>
        <div class="form-group">
          <label for="description">Description</label>
          <textarea class="form-control" id="description" v-model="project.description" required></textarea>
        </div>
        <div class="form-group">
          <label for="requiredFunding">Required Funding</label>
          <input type="number" class="form-control" id="requiredFunding" v-model="project.requiredFunding" required />
        </div>
        <button type="submit" class="btn btn-primary">Update Project</button>
      </form>
    </div>
  </template>
  
  <script>
  import { ref, onMounted } from 'vue';
  import { useRoute, useRouter } from 'vue-router';
  
  export default {
    setup() {
      const project = ref(null);
      const loading = ref(true);
      const error = ref(null);
      const route = useRoute();  // To get the project ID from the URL
      const router = useRouter(); // To redirect after success
      const token = localStorage.getItem('accessToken');
  
      const fetchProject = async () => {
        try {
          const response = await fetch(`/api/creator/projects/${route.params.id}`, {
            headers: {
              'Authorization': `Bearer ${token}`,
              'Content-Type': 'application/json',
            },
          });
          if (!response.ok) {
            const errorText = await response.text();
            throw new Error(`HTTP error ${response.status}: ${errorText}`);
          }
          project.value = await response.json();
        } catch (err) {
          error.value = err.message;
          console.error('Error fetching project:', err);
        } finally {
          loading.value = false;
        }
      };
  
      const updateProject = async () => {
        try {
          const response = await fetch(`/api/creator/projects/${route.params.id}`, {
            method: 'PUT',
            headers: {
              'Authorization': `Bearer ${token}`,
              'Content-Type': 'application/json',
            },
            body: JSON.stringify(project.value),
          });
          if (!response.ok) {
            const errorText = await response.text();
            throw new Error(`HTTP error ${response.status}: ${errorText}`);
          }
          // Redirect to the project list after successful update
          router.push('/creator');
        } catch (err) {
          error.value = err.message;
          console.error('Error updating project:', err);
        }
      };
  
      onMounted(fetchProject);
  
      return { project, loading, error, updateProject };
    },
  };
  </script>
  
  <style scoped>
  /* Φόρμα επεξεργασίας */
  .form-group {
    margin-bottom: 20px;
  }
  
  .form-control {
    width: 100%;
    padding: 10px;
    font-size: 14px;
  }
  
  .btn-primary {
    background-color: #007bff;
    color: white;
  }
  </style>
  