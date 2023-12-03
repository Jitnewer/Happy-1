<script>
import { Research } from '@/models/research'

export default {
  name: 'SuperUserDetail',
  props: ['selectedNews', 'create'],
  emits: ['deselect-news', 'delete-news', 'save-news'],
  data () {
    return {
      copySelected: null,
      edit: false,
      chosenPostType: null
    }
  },
  methods: {
    closeNewsDetail () {
      if (this.edit) {
        if (confirm('Are you sure you don\'t want to save changes?')) {
          this.$emit('deselect-news', this.selectedNews)
        }
      } else {
        this.$emit('deselect-news', this.selectedNews)
      }
    },
    saveNews () {
      if (confirm('Are you sure you want to save changes?')) {
        this.$emit('save-news', this.copySelected)
      }
    },
    deleteNews () {
      if (confirm('Are you sure you want to delete this article?')) {
        this.$emit('delete-news', this.selectedNews)
      }
    },
    activateEdit () {
      this.edit = true
    },
    cancelEdit () {
      if (this.create) {
        this.closeNewsDetail()
      } else {
        if (confirm("Are you sure you don't want to save changes?")) {
          this.edit = false
        }
      }
    },
    activateInput () {
      document.querySelector('#file').click()
    },
    handleImageUpload (event) {
      const file = event.target.files[0]
      if (file !== null) this.copySelected.image = URL.createObjectURL(file)
    }
  },
  computed: {
    isSaveButtonDisabled () {
    // Compare the selectedCopy data with the selectedEvent data
      return (
        this.selectedNews.title === this.copySelected.title &&
        this.selectedNews.link === this.copySelected.link &&
        this.selectedNews.context === this.copySelected.context &&
        this.selectedNews.author === this.copySelected.author &&
        this.selectedNews.publishDate === this.copySelected.publishDate &&
        this.selectedNews.tags === this.copySelected.tags &&
        this.selectedNews.summary === this.copySelected.summary
      )
    }
  },
  created () {
    this.copySelected = Research.copyConstructor(this.selectedNews)
    if (this.create) {
      this.chosenPostType = 'link'
    }
  },
  watch: {
    chosenPostType () {
      if (this.chosenPostType === 'post') {
        this.copySelected.link = null
      }
    }
  }
}
</script>

<template>
  <div class="container">
    <div class="title">
      <button class="back" @click="closeNewsDetail">Back</button>
      <h1>Research</h1>
      <div class="edit-buttons">
        <div v-if="create" class="postType">
          <label>Link</label>
          <input type="radio" value="link" v-model="chosenPostType" autofocus>
          <label>Post</label>
          <input type="radio" value="post" v-model="chosenPostType">
        </div>
        <button @click="activateEdit" v-if="!edit && !create" class="edit">Edit</button>
        <button @click="deleteNews" class="delete" v-if="!create">Delete</button>
        <div v-if="edit || create">
          <button :disabled="isSaveButtonDisabled" @click="saveNews" class="save">Save</button>
          <button @click="cancelEdit" class="cancel">Cancel</button>
        </div>
      </div>
    </div>
    <div v-if="!edit && !create" class="details-container">
      <div class="detail-title">
        <h1> {{ copySelected.title }} </h1>
      </div>
      <div class="details-extra">
        <p>Author:  {{ copySelected.author }} </p>
        <p> || </p>
        <p>Published:  {{ copySelected.publishDate }} </p>
      </div>
      <div class="detail-summary">
        <p> {{ copySelected.summary }} </p>
      </div>
      <div class="detail-image">
        <img :src="copySelected.image">
      </div>
      <div class="detail-context">
        <p> {{ copySelected.context }} </p>
      </div>
    </div>
    <div v-else class="edit-container">
      <div v-if="chosenPostType === 'link' || copySelected.postType === 'link'" class="referenceNews">
        <input type="url" class="linkType" v-model="copySelected.link">
        <div class="file-input">
          <input type="file" accept="image/jpeg, image/png, image/jpg" id="file" @change="handleImageUpload">
          <img @click="activateInput" :src="copySelected.image">
        </div>
      </div>
      <div v-else class="postNews">
        <div class="top">
          <div class="img">
            <input type="file" accept="image/jpeg, image/png, image/jpg" id="file" @change="handleImageUpload">
            <img @click="activateInput" :src="copySelected.image" class="postType-Image">
          </div>
          <div class="inputs">
            <input type="text" name="title" placeholder="Title" v-model="copySelected.title">
            <input type="text" name="author" placeholder="Author" v-model="copySelected.author">
<!--            <input type="text" name="tags">-->
            <textarea v-model="copySelected.summary" placeholder="Summary"></textarea>
          </div>
        </div>
        <textarea class="bottom" v-model="copySelected.context"></textarea>
      </div>
    </div>
  </div>
</template>

<style scoped>
.container {
  padding: 20px;
  margin-left: 0;
}

.title {
  display: flex;
  flex-direction: row;
  gap: 20px;
  height: min-content;
}

h1, h2, h3, h4, h5, h6, p{
  //margin: 0;
  //color: black;
}
</style>
