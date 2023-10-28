export interface Media {
  id: string
  title: string
  type: string
  imageUrl: string
  description: string
  episodes: number
}

export interface UnsavedMedia {
  title: string
  type: string
  imageUrl: string
  description: string
  episodes: number
}

export const mediaTypes = {
  FILM: 'Film',
  SERIES: 'Series'
}
