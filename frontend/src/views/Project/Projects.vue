<template>
    <div class="container mt-5">
      <h1>Projects</h1>
      <table class="table">
        <thead>
          <tr>
            <th>Title</th>
            <th>Creator</th>
            <th>Project Details</th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="project in projects" :key="project.id">
            <td>{{ project.title }}</td>
            <td>{{ project.creator.username}}</td>
            <td>
              <router-link :to="'/project/details/' + project.id" class="btn btn-info">Project details</router-link>
            </td>
          </tr>
        </tbody>
      </table>
        <div v-if="error" class="alert alert-danger">{{ error }}</div>
    
    </div>
  </template>

<script>
import { ref, onMounted } from 'vue';

export default {
  setup() {
    const projects = ref([]);
    const token = localStorage.getItem('accessToken');
    const loading = ref(true);
    const error = ref(null);
    // Συνάρτηση για να τραβήξει τα δεδομένα των έργων
    const fetchProjects = async () => {
      try {
        const response = await fetch('/api/project', {
          headers: {
            'Authorization': `Bearer ${token}`,
            'Content-Type': 'application/json',
          }
        });

        if (!response.ok) {
          throw new Error('Failed to fetch projects');
        }

        // Ενημερώνουμε τη λίστα projects με τα δεδομένα από την απόκριση
        projects.value = await response.json();
      } catch (error) {
        console.error('Error fetching projects:', error);
      } finally {
        loading.value = false; // Σταματάμε το loading όταν ολοκληρωθεί η κλήση
      }
    };

    // Καλούμε τη συνάρτηση fetchProjects μόλις φορτωθεί το component
    onMounted(fetchProjects);

    return { projects, loading, error };
  }
};

</script>
<style scoped>
  /* Basic table styling (you can use Bootstrap classes for more advanced styling) */
  .table {
    width: 100%;
    border-collapse: collapse;
  }
  
  .table th, .table td {
    border: 1px solid #ddd;
    padding: 8px;
    text-align: left;
  }
  
  .table th {
    background-color: #f2f2f2;
  }
  </style>