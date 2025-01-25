<template>
  <div class="container mt-5">
    <!-- Supporter Details Table -->
    <table class="five-column-table">
      <thead>
        <tr>
          <th>Username</th>
          <th>Email</th>
        </tr>
      </thead>
      <tbody>
        <tr>
          <td>{{ supporter.username }}</td>
          <td>{{ supporter.email }}</td>
        </tr>
      </tbody>
    </table>

    <p>Bellow is a list with the projects that the user has supported:</p>

    <!-- List of Contributions -->
    <ul>
      <li v-for="(contribution, index) in supporter.contributions" :key="index">
    <strong>Project:</strong> {{ contribution.project && contribution.project.title ? contribution.project.title : 'No Project Title' }}<br>
    <strong>Amount:</strong> {{ contribution.amount }}<br>
    <strong>Time:</strong> {{ formatTime(contribution.time) }}<br>
  </li>
    </ul>

    <p v-if="error">{{ error }}</p>
  </div>
</template>

<script>
import { ref, onMounted } from 'vue';
import { useRoute, useRouter } from 'vue-router';

export default {
  setup() {
    const route = useRoute();  // Access route parameters
    const router = useRouter();
    const supporter = ref({});
    const token = localStorage.getItem('accessToken');
    const error = ref(null);

    // Fetch supporter details and contributions
    const fetchSupporterData = async () => {
      try {
        const supporterId = route.params.id;  // Get the id from the route parameter
        const response = await fetch(`/api/supporters/${supporterId}`, {
          headers: {
            'Authorization': `Bearer ${token}`,
            'Content-Type': 'application/json',
          },
        });
        if (!response.ok) {
          throw new Error('Failed to fetch supporter data');
        }
        const data = await response.json();
        // Ensure that the project is defined for each contribution
        supporter.value = {
          ...data,
          contributions: data.contributions.map(contribution => ({
            ...contribution,
            project: contribution.project || {}, // Ensure project is not undefined
          })),
        };
      } catch (err) {
        error.value = err.message;
      }
    };

    // Format the time for better readability
    const formatTime = (time) => {
      const date = new Date(time);
      return date.toLocaleString();
    };

    // Logout function
    const logout = () => {
      localStorage.removeItem('accessToken');
      router.push('/login'); // Redirect to login page
    };

    // On component mount, fetch data
    onMounted(() => {
      fetchSupporterData();
    });

    return { supporter, logout, error, formatTime };
  },
};
</script>

<style scoped>
.five-column-table {
  width: 100%;
  border-collapse: collapse;
  margin: 20px 0;
  font-family: Arial, sans-serif;
}

.five-column-table th, .five-column-table td {
  border: 1px solid #dddddd;
  padding: 8px;
  text-align: left;
}

.five-column-table th {
  background-color: #f2f2f2;
  font-weight: bold;
}

.five-column-table tr:nth-child(even) {
  background-color: #f9f9f9;
}

.five-column-table td:hover {
  background-color: #f1f1f1;
}

ul {
  list-style-type: none;
  padding: 0;
}

li {
  background-color: #f0f0f0;
  margin: 5px 0;
  padding: 10px;
  border: 1px solid #ddd;
  border-radius: 5px;
}
</style>
