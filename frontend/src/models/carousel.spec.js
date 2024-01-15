// Carousel.test.js
import { Carousel } from '@/models/carousel.js'

describe('Carousel class', () => {
  test('copyConstructor should create a new Carousel instance with the same properties', () => {
    // Arrange
    const originalCarousel = new Carousel(1, 'Test Title', 'test.jpg', '2024-01-15T12:34:56.789Z')

    // Act
    const copiedCarousel = Carousel.copyConstructor(originalCarousel)

    // Assert
    expect(copiedCarousel).toBeInstanceOf(Carousel)
    expect(copiedCarousel.id).toBe(originalCarousel.id)
    expect(copiedCarousel.title).toBe(originalCarousel.title)
    expect(copiedCarousel.image).toBe(originalCarousel.image)
    expect(copiedCarousel.date).toBe(originalCarousel.date)
  })

  test('copyConstructor should return null when passed null', () => {
    // Act
    const copiedCarousel = Carousel.copyConstructor(null)

    // Assert
    expect(copiedCarousel).toBeNull()
  })
  test('copyConstructor should handle undefined properties', () => {
    // Arrange
    const originalCarousel = new Carousel(1, undefined, undefined, undefined)

    // Act
    const copiedCarousel = Carousel.copyConstructor(originalCarousel)

    // Assert
    expect(copiedCarousel).toBeInstanceOf(Carousel)
    expect(copiedCarousel.id).toBe(originalCarousel.id)
    expect(copiedCarousel.title).toBeUndefined()
    expect(copiedCarousel.image).toBeUndefined()
    expect(copiedCarousel.date).toBe(originalCarousel.date)
  })
})
