export interface Product {
  id: string,
  header: object,
  primaryImageId: string,
  type: string,
  name: string,
  category: string,
  subcategory: string,
  foodProperties: {
    energyValue: number,
    proteins: number,
    fats: number,
    saturatedFattyAcids: number,
    monoUnsaturatedFattyAcids: number,
    polyUnsaturatedFattyAcids: number,
    cholesterol: number,
    carbohydrates: number,
    sucrose: number,
    dietaryFibres: number,
    sodium: number,
    potassium: number,
    calcium: number,
    phosphorus: number,
    magnesium: number,
    iron: number,
    selenium: number,
    betaCarotene: number,
    vitaminD: number,
    vitaminC: number,
  },
  lactose: boolean,
  starch: boolean,
  gluten: boolean,
}