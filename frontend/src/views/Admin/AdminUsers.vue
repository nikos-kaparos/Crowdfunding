<template>
    <div class="container mt-5">  
      <h1>Users</h1>
      <table class="table">
        <thead>
          <tr>
            <th>Username</th>
            <th>Role</th>
            <th>Status</th>
            <th>Actions</th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="user in users" :key="user.id">
            <td>{{ user.username }}</td>
            <td>{{ user.role }}</td>
            <td>{{ user.enabled ? 'Enabled' : 'Disabled' }}</td>
            <td>
              <button @click="toggleUserStatus(user)" class="btn btn-sm"
                :class="user.enabled ? 'btn-danger' : 'btn-success'">
                {{ user.enabled ? 'Disable' : 'Enable' }}
              </button>
              <button @click="deleteUser(user.id)" class="btn btn-sm btn-danger ml-2">
                Delete
              </button>
            </td>
          </tr>
        </tbody>
      </table>
          <div v-if="error" class="alert alert-danger">{{ error }}</div>
  
    </div>
  </template>
  
  <script>
  import { ref, onMounted } from 'vue';
  import { useRouter } from 'vue-router'
  
  export default {
    setup() {
      const users = ref([]);
      const loading = ref(true);
      const username = ref(localStorage.getItem('username') || "Guest");
      const token = localStorage.getItem('accessToken');
      const error = ref(null);
      const router = useRouter()
  
      const fetchUsers = async () => {
        try {
          const response = await fetch('/api/admin/users', {
            headers: {
              'Authorization': `Bearer ${token}`,
              'Content-Type': 'application/json',
            },
          });
          if (!response.ok) {
            const errorText = await response.text()
            throw new Error(`HTTP error ${response.status}: ${errorText}`);
          }
          users.value = await response.json();
        } catch (err) {
                  error.value=err.message;
          console.error("Error fetching users:", err);
        } finally {
          loading.value = false;
        }
      };
  
      onMounted(fetchUsers);
  
      const toggleUserStatus = async (user) => {
        try {
          const response = await fetch(`/api/admin/users/${user.id}/enable?enabled=${!user.enabled}`, {
            method: 'POST',
            headers: {
              'Authorization': `Bearer ${token}`,
            },
          });
            if (!response.ok) {
            const errorText = await response.text()
            throw new Error(`HTTP error ${response.status}: ${errorText}`);
          }
          // Refresh the user list after successful update
          await fetchUsers();
        } catch (err) {
          console.error("Error toggling user status:", err);
                  error.value=err.message;
        }
      };

      const deleteUser = async (userId) => {
      if (confirm("Are you sure you want to delete this user?")) {
        try {
          const response = await fetch(`/api/admin/users/${userId}`, {
            method: 'DELETE',
            headers: {
              'Authorization': `Bearer ${token}`,
              'Content-Type': 'application/json',
            },
          });
          if (!response.ok) {
            const errorText = await response.text()
            throw new Error(`HTTP error ${response.status}: ${errorText}`);
          }
          // Refresh the user list after successful deletion
          await fetchUsers();
        } catch (err) {
          console.error("Error deleting user:", err);
          error.value = err.message;
        }
      }
    };
  
      return { users, loading, username, toggleUserStatus,error, deleteUser};
    },
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