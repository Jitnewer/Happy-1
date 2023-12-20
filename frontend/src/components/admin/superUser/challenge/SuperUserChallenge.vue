<script>
import { Challenge } from '@/models/challenge'
import { Paragraph } from '@/models/paragraph'

export default {
  name: 'SuperUserChallenge',
  inject: ['challengeService'],
  data () {
    return {
      challenge: null,
      copyChallenge: null,
      edit: false,
      create: false
    }
  },
  methods: {
    back () {
      this.$router.push({ name: 'SuperUserChallenges' })
    },
    async save () {
      if (this.validateFirstParagraph && this.validateTitle) {
        if (confirm('Are you sure you want to save changes?')) {
          // Remove all empty paragraphs and corresponding title
          this.copyChallenge.dateTime = new Date()
          for (const paragraph of this.copyChallenge.paragraphs) {
            if (paragraph.title === null || paragraph.content === null || paragraph.title.trim() === '' || paragraph.content.trim() === '') {
              const index = this.copyChallenge.paragraphs.findIndex(emptyParagraph => emptyParagraph.id === paragraph.id)

              this.copyChallenge.paragraphs.splice(index, 1)
            }
          }

          console.log(this.copyChallenge)
          await this.challengeService.asyncSave(this.copyChallenge)

          // this.back()
        }
      }
    },
    cancel () {
      if (this.create) {
        if (confirm('Are you sure you want to discard changes?')) {
          this.back()
        }
      } else {
        if (confirm('Are you sure you want to discard changes?')) {
          this.edit = false
          this.copyChallenge = Challenge.copyConstructor(this.selectedChallenge)
        }
      }
    },
    editChallenge () {
      this.edit = true
    },
    deleteChallenge () {
      // if (confirm('Are you sure you want to delete this challenge?')) {
      //   this.$emit('delete', this.selectedChallenge)
      // }
    },
    activateInput () {
      document.querySelector('#file').click()
    },
    handleImageUpload (event) {
      const file = event.target.files[0]
      this.selectedCopy.image = URL.createObjectURL(file)
    },
    formattedDateTime (dateTime) {
      if (dateTime) return null
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
    addParagraph () {
      let newId = 0
      if (this.copyChallenge.paragraphs.length > 0) {
        newId = this.copyChallenge.paragraphs.reduce((max, obj) => (obj.id > max ? obj.id : max), 0)
      }
      const paragraph = new Paragraph(0, null, null)
      this.copyChallenge.paragraphs.push(paragraph)
    },
    removeParagraph (paragraph) {
      if (confirm('Are you sure you want to delete this paragraph?')) {
        let index
        if (paragraph.tempId) {
          index = this.copyChallenge.paragraphs.findIndex(paragraphToDelete => paragraphToDelete.tempId === paragraph.tempId)
        } else {
          index = this.copyChallenge.paragraphs.findIndex(paragraphToDelete => paragraphToDelete.id === paragraph.id)
        }
        if (index >= 0) {
          this.copyChallenge.paragraphs.splice(index, 1)
        }
      }
    }
  },
  computed: {
    validateFirstParagraph () {
      if (this.copyChallenge.firstParagraph === null) return false
      if (this.copyChallenge.firstParagraph.trim() === '') return false
      return true
    },
    validateTitle () {
      if (this.copyChallenge.title === null) return false

      if (this.copyChallenge.title.trim() === '') return false
      if (this.copyChallenge.title.length > 255) return false

      return true
    },
    createNewChallenge () {
      return new Challenge(
        0,
        null,
        null,
        'assets/challengePic/imagePlaceholder.jpg',
        null,
        []
      )
    }
  },
  async created () {
    const challengeId = parseInt(this.$route.params.id)

    if (challengeId === 0) {
      this.challenge = this.createNewChallenge
      this.create = true
      this.copyChallenge = Challenge.copyConstructor(this.challenge)
    }

    if (challengeId) {
      this.challenge = await this.challengeService.asyncFindById(challengeId)
      this.copyChallenge = Challenge.copyConstructor(this.challenge)
    }
  }
}
</script>

<template>
  <div class="container" v-if="copyChallenge">
    <div class="challenge-main">
      <div class="challenge-title">
        <button @click="back">Back</button>
        <h1>Challenge</h1>

        <div class="challenge-edit-buttons">
          <button v-if="!edit && !create" @click="editChallenge">Edit</button>
          <button v-if="edit || create" @click="cancel">Cancel</button>
          <button v-if="edit || create" @click="save">Save</button>
          <button v-if="!create" class="delete" @click="deleteChallenge">Delete</button>
        </div>
      </div>
      <div class="detail-challenge">
        <div>
          <img :src="require(`../../../../${copyChallenge.image}`)" alt="Challenge Image" @click="activateInput"/>
          <input v-if="create || edit" type="file" accept="image/jpeg, image/png, image/jpg" id="file" @change="handleImageUpload">
        </div>
        <div class="content" v-if="!edit && !create">
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
            <input type="text" class="challenge-input" v-model="paragraph.title">
            <textarea class="challenge-input" id="paragraph-content" v-model="paragraph.content"></textarea>
            <button class="delete-paragraph" @click="removeParagraph(paragraph)"><i class="fa-solid fa-x"></i></button>
          </div>
          <button class="add-paragraph" @click="addParagraph"><i class="fa-solid fa-plus"></i></button>
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
