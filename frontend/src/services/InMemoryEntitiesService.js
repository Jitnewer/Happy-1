/**
 * Class for managing in-memory entities.
 */
export class InMemoryEntitiesService {
  _lastId;
  _entities;

  /**
   * Getter for entities property.
   * @returns {Array} - The array of entities.
   */
  get entities () {
    return this._entities
  }

  /**
   * Constructor for InMemoryEntitiesService.
   * @param {number} initialId - The initial ID for entities (default is 10000).
   * @param {Function} sampleCreator - A function to create sample entities (optional).
   */
  constructor (initialId = 10000, sampleCreator = null) {
    this._lastId = initialId
    this._entities = []

    // Create sample entities if a sampleCreator function is provided
    if (sampleCreator != null) {
      for (let i = 0; i < 7; i++) {
        this.save(sampleCreator(0))
      }
    }
  }

  /**
   * Retrieves all entities.
   * @returns {Array} - The array of entities.
   */
  findAll () {
    return this._entities
  }

  /**
   * Finds an entity by ID.
   * @param {number|string} id - The ID of the entity to find.
   * @returns {Object|undefined} - The found entity or undefined if not found.
   */
  findById (id) {
    for (let i = 0; i < this._entities.length; i++) {
      if (this._entities[i].id.toString() === id.toString()) {
        this._entities = this._entities[i]
        return this._entities[i]
      }
    }
  }

  /**
   * Saves an entity to the in-memory storage.
   * @param {Object} entity - The entity to save.
   * @returns {Object} - The saved entity.
   */
  save (entity) {
    if (entity == null) return

    const index = this._entities.findIndex(e => e?.id === entity.id)

    if (index >= 0) {
      this._entities[index] = entity
    } else {
      if (entity?.id === 0 || entity?.id === undefined) entity.id = ++this._lastId
      this._entities.push(entity)
    }

    return entity
  }

  /**
   * Deletes an entity by ID from the in-memory storage.
   * @param {number|string} id - The ID of the entity to delete.
   */
  deleteById (id) {
    this._entities = this._entities.filter(e => e?.id !== id)
  }
}
