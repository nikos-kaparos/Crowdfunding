<template>
    <div class="container mt-5">
  
      <p>Bellow are the available Supporters!</p>
      
      <!-- Table displaying supporters -->
      <table class="three-column-table">
        <thead>
          <tr>
            <th>Username</th>
            <th>Email</th>
            <th>Details</th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="sup in supporters" :key="sup.id">
            <td>{{ sup.username }}</td>
            <td>{{ sup.email }}</td>
            <td>
              <router-link :to="'/supporter/supporters-details/' + sup.id" class="btn btn-info">
                Supporter Details
              </router-link>
            </td>
          </tr>
        </tbody>
      </table>
    </div>
  </template>

<script>
import { ref, onMounted } from 'vue';
import { useRouter } from 'vue-router';

export default {
  setup() {
    const router = useRouter();
    const supporters = ref([]);
    const username = ref(''); // Assuming you will fetch the username from the auth system
    const token = localStorage.getItem('accessToken');
    const error = ref(null);

    // Fetch the supporters list when the component is mounted
    const fetchSupporters = async () => {
      try {
        const response = await fetch('/api/supporters', {
          headers: {
            'Authorization': `Bearer ${token}`,
            'Content-Type': 'application/json',
          },
        });
        if (!response.ok) {
          throw new Error('Failed to fetch supporters');
        }
        supporters.value = await response.json();
      } catch (err) {
        error.value = err.message;
      }
    };

    // Fetch username from storage or API (if needed)
    const fetchUsername = () => {
      // You can get the username from your authentication system
      username.value = localStorage.getItem('username') || 'Guest';
    };

    // Logout function
    const logout = () => {
      localStorage.removeItem('accessToken');
      localStorage.removeItem('username');
      router.push('/login'); // Redirect to login page
    };

    // On component mount, fetch data
    onMounted(() => {
      fetchSupporters();
      fetchUsername();
    });

    return { supporters, username, logout, error };
  },
};
</script>

<style scoped>
.three-column-table {
  width: 100%;
  border-collapse: collapse;
  margin: 20px 0;
  font-family: Arial, sans-serif;
}

.three-column-table th, .three-column-table td {
  border: 1px solid #dddddd;
  padding: 8px;
  text-align: left;
}

.three-column-table th {
  background-color: #f2f2f2;
  font-weight: bold;
}

.three-column-table tr:nth-child(even) {
  background-color: #f9f9f9;
}

.three-column-table td:hover {
  background-color: #f1f1f1;
}
</style>