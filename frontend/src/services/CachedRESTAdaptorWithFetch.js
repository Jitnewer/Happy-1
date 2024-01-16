import { RESTAdaptorWithFetch } from '@/services/RESTAdaptorWithFetch'
import CustomError from '@/CustomError'

/**
 * Extended class for handling cached REST operations with fetch.
 */
export class CachedRESTAdaptorWithFetch extends RESTAdaptorWithFetch {
  entities;
  entity;

  /**
   * Constructor for CachedRESTAdaptorWithFetch.
   * @param {string} resourceUrl - The base URL for the resource.
   * @param {Function} copyConstructor - A function to copy the object.
   */
  constructor (resourceUrl, copyConstructor) {
    super(resourceUrl, copyConstructor)
    this.entities = []
    this.entity = null
  }

  /**
   * Asynchronously fetches all entities.
   * @returns {Promise<Array>} - A promise that resolves to an array of entities.
   * @throws {CustomError} - Throws a custom error if the operation fails.
   */
  async asyncFindAll () {
    try {
      this.entities = await super.asyncFindAll()
      return this.entities
    } catch (error) {
      throw new CustomError('Error in asyncFindAll', error.status || 500, error.error)
    }
  }

  /**
   * Asynchronously fetches an entity by ID.
   * @param {string} id - The ID of the entity to fetch.
   * @returns {Promise<Object>} - A promise that resolves to the fetched entity.
   * @throws {CustomError} - Throws a custom error if the operation fails.
   */
  async asyncFindById (id) {
    try {
      const entity = await super.asyncFindById(id)

      // Update or add the entity to the cache
      this.entities = entity

      return entity
    } catch (error) {
      throw new CustomError('Error in asyncFindById', error.status, error.error)
    }
  }

  /**
   * Asynchronously saves an entity.
   * @param {Object} data - The data of the entity to save.
   * @returns {Promise<Object>} - A promise that resolves to the saved entity.
   * @throws {CustomError} - Throws a custom error if the operation fails.
   */
  async asyncSave (data) {
    try {
      const newEntity = await super.asyncSave(data)

      // Add the new entity to the cache
      if (Array.isArray(this.entities)) {
        this.entities.push(newEntity)
      }

      return newEntity
    } catch (error) {
      throw new CustomError('Error in asyncSave', error.status, error.error)
    }
  }

  /**
   * Asynchronously sends a newsletter.
   * @param {Object} object - The newsletter object to send.
   * @returns {Promise<Object>} - A promise that resolves to the response.
   * @throws {CustomError} - Throws a custom error if the operation fails.
   */
  async asyncSendNewsletter (object) {
    try {
      return await super.asyncSendNewsletter(object)
    } catch (error) {
      throw new CustomError('Error in asyncSendNewsletter', error.toJSON().status || 500, error.toJSON().error)
    }
  }

  /**
   * Asynchronously deletes an entity by ID.
   * @param {string} id - The ID of the entity to delete.
   * @returns {Promise<void>} - A promise that resolves when the deletion is successful.
   * @throws {CustomError} - Throws a custom error if the operation fails.
   */
  async asyncDeleteById (id) {
    try {
      // Delete the entity from the server
      return await super.asyncDeleteById(id)
    } catch (error) {
      throw error
    }
  }

  /**
   * Asynchronously finds entities by mail.
   * @param {string} mail - The mail to search for.
   * @returns {Promise<Array>} - A promise that resolves to an array of entities.
   * @throws {CustomError} - Throws a custom error if the operation fails.
   */
  async asyncFindByMail (mail) {
    try {
      return await super.asyncFindByMail(mail)
    } catch (e) {
      throw new CustomError('Error in asyncFindByMail', e.status, e.error)
    }
  }

  /**
   * Asynchronously adds an entity to another entity.
   * @param {string} id1 - The ID of the first entity.
   * @param {string} id2 - The ID of the second entity.
   * @param {string} url - The URL for the operation.
   * @returns {Promise<Object>} - A promise that resolves to the response.
   * @throws {CustomError} - Throws a custom error if the operation fails.
   */
  async asyncAddEntityToEntity (id1, id2, url) {
    try {
      const response = await super.asyncAddEntityToEntity(id1, id2, url)

      // Invalidate the cache for related entities (assuming a single cache for all entities)
      this.entities = []

      return response
    } catch (error) {
      throw new CustomError('Error in asyncAddEntityToEntity', error.status, error.error)
    }
  }

  /**
   * Asynchronously removes an entity from another entity.
   * @param {string} id1 - The ID of the first entity.
   * @param {string} id2 - The ID of the second entity.
   * @param {string} url - The URL for the operation.
   * @returns {Promise<boolean>} - A promise that resolves to a boolean indicating success.
   * @throws {CustomError} - Throws a custom error if the operation fails.
   */
  async asyncRemoveEntityFromEntity (id1, id2, url) {
    try {
      const isRemoved = await super.asyncRemoveEntityFromEntity(id1, id2, url)

      if (isRemoved) {
        // Invalidate the cache for related entities (assuming a single cache for all entities)
        this.entities = []
      }

      return isRemoved
    } catch (error) {
      throw new CustomError('Error in asyncRemoveEntityFromEntity', error.status, error.error)
    }
  }

  /**
   * Asynchronously finds entities by column, implementing caching logic.
   * @param {string} column - The column to search for.
   * @param {string} url - The URL for the operation.
   * @returns {Promise<Array>} - A promise that resolves to an array of entities.
   * @throws {CustomError} - Throws a custom error if the operation fails.
   */
  async asyncFindByColumn (column, url) {
    try {
      // Check if data is already in the cache
      if (this.entities.length === 0) {
        this.entities = await super.asyncFindAll()
      }

      // Implement caching logic based on your specific requirements
      const cachedData = this.entities.filter(e => e.column === column)

      if (cachedData.length > 0) {
        return cachedData
      }

      // Fetch data from the server if not in the cache
      const response = await super.asyncFindByColumn(column, url)

      // Update the cache
      this.entities = this.entities.concat(response)

      return response
    } catch (error) {
      throw new CustomError('Error in asyncFindByColumn', error.status, error.error)
    }
  }
}
