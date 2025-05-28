<template>
    <div class="container mt-5">
      <h1>My Projects</h1>
      <div v-if="projects.length === 0">
        <p>You have no projects yet.</p>
      </div>
      <div v-if="loading" class="alert alert-info">Loading projects...</div>
      <div v-if="error" class="alert alert-danger">{{ error }}</div>
      <table class="table" v-else>
        <thead>
          <tr>
            <th>Title</th>
            <th>Description</th>
            <th>Funding</th>
            <th>Actions</th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="project in projects" :key="project.id">
            <td>{{ project.title }}</td>
            <td>{{ project.description }}</td>
            <td>{{ project.totalFunding }} / {{ project.requiredFunding }}</td>
            <td>
                <button @click="goToEditPage(project.id)" class="btn btn-sm btn-warning">Edit</button>
                <button @click="deleteProject(project.id)" class="btn btn-sm btn-danger">Delete</button>
            </td>
          </tr>
        </tbody>
      </table>
    </div>
  </template>
  
  <script>
  import { ref, onMounted } from 'vue';
import { useRouter } from 'vue-router'; // Εισαγωγή του Vue Router

export default {
  setup() {
    const router = useRouter(); // Χρήση του router
    const projects = ref([]);
    const loading = ref(true);
    const error = ref(null);
    const token = localStorage.getItem('accessToken');

    const fetchProjects = async () => {
      try {
        const response = await fetch('/api/creator/my-projects', {
          headers: {
            'Authorization': `Bearer ${token}`,
            'Content-Type': 'application/json',
          },
        });
        if (!response.ok) {
          const errorText = await response.text();
          throw new Error(`HTTP error ${response.status}: ${errorText}`);
        }
        projects.value = await response.json();
      } catch (err) {
        error.value = err.message;
        console.error('Error fetching projects:', err);
      } finally {
        loading.value = false;
      }
    };

    const deleteProject = async (projectId) => {
      if (confirm('Are you sure you want to delete this project?')) {
        try {
          const response = await fetch(`/api/creator/projects/${projectId}`, {
            method: 'DELETE',
            headers: {
              'Authorization': `Bearer ${token}`,
            },
          });
          if (!response.ok) {
            const errorText = await response.text();
            throw new Error(`HTTP error ${response.status}: ${errorText}`);
          }
          // Refresh the project list after deletion
          await fetchProjects();
        } catch (err) {
          console.error('Error deleting project:', err);
          error.value = err.message;
        }
      }
    };

    const goToEditPage = (projectId) => {
      // Χρησιμοποιούμε το router.push για να κατευθυνθούμε στην σελίδα επεξεργασίας
      router.push(`/creator-update/${projectId}`);
    };

    onMounted(fetchProjects);

    return { projects, loading, error, deleteProject, goToEditPage };
  },
};

  </script>
  
  <style scoped>
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
  
  .btn {
    padding: 5px 10px;
    font-size: 14px;
  }
  
  .btn-warning {
    background-color: #f0ad4e;
    color: white;
  }
  
  .btn-danger {
    background-color: #d9534f;
    color: white;
  }
  
  .alert {
    margin-top: 20px;
  }
  </style>
  