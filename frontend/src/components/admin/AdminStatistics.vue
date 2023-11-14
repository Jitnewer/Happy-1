<template>
  <div class="container">
    <div class="users-amount-container">
      <div class="legend">
        <p>Total Users: {{ totalUsersCount }}</p>
        <p class="entrepreneur">Entrepreneurs: {{ entrepreneurCount }}</p>
        <p class="partner">Partners: {{ partnerCount }}</p>
        <p class="admin">Admins: {{ adminCount }}</p>
        <p class="superUser">SuperUsers: {{ superuserCount }}</p>
      </div>
      <div class="canvas-container">
        <canvas class="block-chart" id="chart"></canvas>
      </div>
    </div>
  </div>
</template>

<script>
import Chart from 'chart.js/auto' // Import Chart.js

export default {
  name: 'AdminStatistics',
  data () {
    return {
      totalUsersCount: 50,
      entrepreneurCount: 20,
      partnerCount: 25,
      adminCount: 3,
      superuserCount: 2
    }
  },
  mounted () {
    this.createBarChart()
  },
  methods: {
    createBarChart () {
      const chart = document.getElementById('chart').getContext('2d')
      // eslint-disable-next-line no-new
      new Chart(chart, {
        type: 'bar',
        data: {
          labels: ['Entrepreneurs', 'Partners', 'Admin', 'Superuser'],
          datasets: [
            {
              data: [
                this.entrepreneurCount,
                this.partnerCount,
                this.adminCount,
                this.superuserCount
              ],
              backgroundColor: [
                'blue',
                'green',
                'orange',
                'red'
              ]
            }
          ]
        },
        options: {
          indexAxis: 'y', // Set the y-axis as the category axis
          scales: {
            x: {
              beginAtZero: true
            },
            y: {
              beginAtZero: true
            }
          },
          legend: {
            display: false
          }
        }
      })
    }
  }
}
</script>

<style scoped>

.users-amount-container {
  height: 80%;
  width: 90%;
  margin-top: 30px;
  display: flex;
  flex-direction: row;
  justify-content: space-around;
}

.canvas-container {
  width: 600px;
}

p {
  //color: black;
}

.entrepreneur {
  color: blue;
}

.partner {
  color: green;
}

.admin {
  color: orange;
}

.superUser {
  color: red;
}

.legend {
  margin-top: 30px;
}

@media (max-width: 800px) {
  .canvas-container {
    width: 300px;
  }

  p {
    //font-size: 14px;
  }
}
</style>
