import { Research } from '@/models/research'

const RESEARCH_ID = 330
const RESEARCH_TITLE = 'Research Title'
const RESEARCH_DATE_TIME = '2019-01-01T00:00:00.000Z'
const RESEARCH_IMAGE = 'img.png'
const RESEARCH_FIRST_PARAGRAPH = 'Research first paragraph'
const RESEARCH_PARAGRAPHS = ['Research paragraph 1', 'Research paragraph 2']
const RESEARCH_THEME = 'Research theme'

let research1, research2

beforeEach(() => {
  research1 = new Research(RESEARCH_ID, RESEARCH_TITLE, RESEARCH_DATE_TIME, RESEARCH_IMAGE, RESEARCH_FIRST_PARAGRAPH, RESEARCH_PARAGRAPHS, RESEARCH_THEME)
  research2 = new Research(RESEARCH_ID + 1, RESEARCH_TITLE, RESEARCH_DATE_TIME, RESEARCH_IMAGE, RESEARCH_FIRST_PARAGRAPH, RESEARCH_PARAGRAPHS, RESEARCH_THEME)
})

it('should create a research object', () => {
  expect(research1.id).toBe(RESEARCH_ID)
  expect(research1.title).toBe(RESEARCH_TITLE)
  expect(research1.dateTime).toBe(RESEARCH_DATE_TIME)
  expect(research1.image).toBe(RESEARCH_IMAGE)
  expect(research1.firstParagraph).toBe(RESEARCH_FIRST_PARAGRAPH)
  expect(research1.paragraphs).toBe(RESEARCH_PARAGRAPHS)
  expect(research1.theme).toBe(RESEARCH_THEME)
})

it('should make a new research object when given a research object', () => {
  const research3 = Research.copyConstructor(research1)
  expect(research3).toEqual(research1)
})
