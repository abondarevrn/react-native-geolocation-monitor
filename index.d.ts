declare module "react-native-geolocation-monitor" {
  export interface GeoError {
    code: PositionError;
    message: string;
  }

  export interface GeoCoordinates {
    latitude: number;
    longitude: number;
  }

  export function init(): void;
  export function requestAuthorization(): void;

  export function getCurrentPosition(
    successCallback: SuccessCallback,
    errorCallback?: ErrorCallback,
    options?: GeoOptions
  ): Promise<GeoPosition>;

  export function watchPosition(
    successCallback: SuccessCallback,
    errorCallback?: ErrorCallback,
    options?: GeoWatchOptions
  ): number;

  type SuccessCallback = (position: GeoPosition) => void;
  type ErrorCallback = (error: GeoError) => void;

  export function clearWatch(watchID: number): void;
  export function stopObserving(): void;
}
