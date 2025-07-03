// function which will convert stream data to json format
export async function ConvertToJSONFromStream(response) {
  const responseText = await response.text();

  return JSON.parse(responseText);
}
