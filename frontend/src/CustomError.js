class CustomError extends Error {
  constructor (message, status, error) {
    super(message)
    this.status = status
    this.error = error
  }

  toJSON () {
    return {
      message: this.message,
      status: this.status,
      error: this.error
    }
  }
}

export default CustomError
