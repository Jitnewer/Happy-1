export class InMemmoryEntityService {
  _lastId
  _entities
  get entities () {
    return this._entities
  }

  constructor (initialId = 1, sampleCreator = null) {
    this._lastId = initialId
    this._entities = []
    if (sampleCreator != null) {
      for (let i = 0; i < 7; i++) {
        this.asyncSave(sampleCreator(0))
      }
    }
  }

  asyncFindAll () {
    return this._entities
  }

  asyncFindById (id) {
    const foundEntity = this._entities.find(e => e?.id === id)
    return foundEntity
  }

  asyncSave (entity) {
    if (entity === null) return
    const index = this._entities.findIndex(e => e?.id === entity.id)
    if (index >= 0) {
      this._entities[index] = entity
    } else {
      if (entity.id === 0 || entity.id === undefined) entity.id = this._lastId++
      this._entities.push(entity)
    }
    return entity
  }

  asyncDeleteById (id) {
    this._entities = this._entities.filter(e => e.id !== id)
  }
}
