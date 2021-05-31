export interface Meal {
  id: string,
  name: string,
  dayMealId: string,
  foodId: string,
  isProduct: number,
  productList: any[],
  grams: number,
  portions: number,
  recipe: string,
  foodType: string
}
