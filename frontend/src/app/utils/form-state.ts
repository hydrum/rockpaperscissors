export interface FormStateModel<T> {
    model?: T;
    dirty: boolean;
    status: string;
    errors: any;
}

export function defaultFormStateModel<T>(): FormStateModel<T> {
    return {
        model: undefined,
        dirty: false,
        status: '',
        errors: {},
    };
}
