export interface IPurchase {
  id: number
  name: string
  date: string
  price: number
  status: 'active' | 'canceled' | 'expired'
  recieptUrl?: string
}

export interface IPurchasesResponse {
  content: IPurchase[]
  totalItems: number
}
