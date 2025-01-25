<template>
  <div class="container mt-5">
    <div v-if="project">
      <div class="item">
        <strong>Title</strong>
        <p>{{ project.title }}</p>
      </div>

      <div class="item">
        <strong>Required Funding</strong>
        <p>{{ project.requiredFunding }}</p>
      </div>

      <div class="item">
        <strong>Funding till now</strong>
        <p>{{ project.totalFunding }}</p>
      </div>

      <div class="item">
        <strong>Funding to go</strong>
        <p>{{ project.requiredFunding - project.totalFunding }}</p>
      </div>

      <div class="item">
        <strong>Description</strong>
        <p class="description">{{ project.description }}</p>
      </div>

      <div class="item">
        <strong>Created by</strong>
        <p class="description">{{ project.creator.username }}</p>
      </div>

      <div class="item">
        <form @submit.prevent="supportProject">
          <p>Support if you want!!!!</p>

          <div>
            <label for="amount">Amount</label>
            <input id="amount" v-model="supportAmount" type="number" name="amount" placeholder="Amount" required />
          </div>

          <!-- Automatically set supporterId with the logged-in user's ID -->
          <input type="hidden" :value="userId" />

          <button type="submit">Click to confirm</button>
        </form>
      </div>
    </div>

    <div v-if="error" class="alert alert-danger mt-3">
      <p>{{ error }}</p>
    </div>

    <div v-if="isLoading" class="alert alert-info mt-3">
      <p>Loading project data...</p>
    </div>
  </div>
</template>

<script>
import { ref, onMounted } from 'vue';
import { useRoute } from 'vue-router';

export default {
  setup() {
    const route = useRoute();
    const project = ref(null);
    const supportAmount = ref('');
    const error = ref(null);
    const isLoading = ref(false);
    const token = localStorage.getItem('accessToken');
    // const userId = localStorage.getItem('userId'); // Assuming you store the user ID in localStorage

    // Fetch project details
    const fetchProjectData = async () => {
      isLoading.value = true;
      try {
        const projectId = route.params.id;
        const response = await fetch(`/api/project/details/${projectId}`, {
          headers: {
            'Authorization': `Bearer ${token}`,
            'Content-Type': 'application/json',
          },
        });
        if (!response.ok) {
          throw new Error('Failed to fetch project details');
        }
        project.value = await response.json();
      } catch (err) {
        error.value = err.message;
      } finally {
        isLoading.value = false;
      }
    };

    // Support a project
    const supportProject = async () => {
      try {
        const projectId = route.params.id;
        const response = await fetch(`/api/project/support/${projectId}`, {
          method: 'POST',
          headers: {
            'Authorization': `Bearer ${token}`,
            'Content-Type': 'application/json',
          },
          body: JSON.stringify({
            amount: supportAmount.value,
          }),
        });
        if (!response.ok) {
          throw new Error('Failed to support project maybe you are suppot this');
        }
        const data = await response.json();
        alert(data.message);
        fetchProjectData(); // Refresh project details
      } catch (err) {
        error.value = err.message;
      }
    };

    onMounted(() => {
      fetchProjectData();
    });

    return { project, supportAmount, supportProject, error, isLoading };
  },
};
</script>

<style scoped>
/* Copy your existing CSS here */
.container {
  display: flex;
  flex-direction: column;
  gap: 20px;
  max-width: 600px;
  margin: 0 auto;
}

.item {
  background-color: #f9f9f9;
  padding: 15px;
  border-radius: 8px;
  box-shadow: 0 0 5px rgba(0, 0, 0, 0.1);
}

.item strong {
  display: block;
  font-size: 1.1em;
  margin-bottom: 8px;
}

.description {
  font-size: 1em;
  line-height: 1.6;
  word-wrap: break-word;
}
</style>