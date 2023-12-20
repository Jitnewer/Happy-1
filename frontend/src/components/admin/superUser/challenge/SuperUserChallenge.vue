<script>
import { Challenge } from '@/models/challenge'

export default {
  name: 'SuperUserChallenge',
  props: ['selectedChallenge', 'create'],
  emits: ['back', 'save', 'delete'],
  data () {
    return {
      copyChallenge: null,
      edit: false
    }
  },
  methods: {
    back () {
      this.$emit('back')
    },
    save () {
      if (confirm('Are you sure you want to save changes?')) {
        this.$emit('save', this.copyChallenge)
      }
    },
    deleteChallenge () {
      if (confirm('Are you sure you want to delete this challenge?')) {
        this.$emit('delete', this.selectedChallenge)
      }
    },
    formattedDateTime (dateTime) {
      const localDateTime = new Date(dateTime)

      const options = {
        day: '2-digit',
        month: 'short',
        year: 'numeric',
        hour: '2-digit',
        minute: '2-digit'
      }

      return localDateTime.toLocaleDateString('nl-NL', options)
    },
    activateInput () {
      document.querySelector('#file').click()
    },
    handleImageUpload (event) {
      const file = event.target.files[0]
      this.selectedCopy.image = URL.createObjectURL(file)
      console.log(this.selectedCopy.image)
    }
  },
  created () {
    this.copyChallenge = Challenge.copyConstructor(this.selectedChallenge)
  }
}
</script>

<template>
  <div class="container">
    <div class="challenge-main">
      <div class="challenge-title">
        <button @click="back">Back</button>
        <h1>Challenge</h1>

        <div class="challenge-edit-buttons">
          <button @click="edit">Edit</button>
          <button @click="deleteChallenge">Delete</button>
        </div>
      </div>
      <div class="detail-challenge">
        <div>
          <img :src="require(`../../../../assets/images/${copyChallenge.image}`)" alt="Challenge Image" @click="activateInput"/>
          <input v-if="create || edit" type="file" accept="image/jpeg, image/png, image/jpg" id="file" @change="handleImageUpload">
        </div>
        <div class="content" v-if="!create">
          <div>
            <p class="challenge-time">{{ formattedDateTime(copyChallenge.dateTime) }}</p>
            <h2>{{ copyChallenge.title }}</h2>
            <p>{{ copyChallenge.firstParagraph}} </p>
          </div>
          <div v-for="paragraph in copyChallenge.paragraphs" :key="paragraph.id">
            <h4>{{ paragraph.title }}</h4>
            <p>{{ paragraph.content }}</p>
          </div>
        </div>
        <div v-else class="content">
          <div>
            <input type="text" placeholder="Challenge title" class="challenge-input" id="challenge-title" v-model="copyChallenge.title">
            <textarea class="challenge-input" id="challenge-firstParagraph" v-model="copyChallenge.firstParagraph"></textarea>
          </div>
          <div v-for="paragraph in copyChallenge.paragraphs" :key="paragraph.id">
            <input type="text" class="challenge-input" id="paragraph-title" v-model="paragraph.title">
            <textarea class="challenge-input" id="paragraph-content" v-model="paragraph.content"></textarea>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<style scoped>
.container {
  padding: 20px;
  margin-left: 0;
}

h1, h2, h3, h4, h5, h6, p{
  color: black;
}

.content {
  display: flex;
  flex-direction: column;
  gap: 20px;
}

.content div {
  display: flex;
  flex-direction: column;
  gap: 10px;
}
</style>
