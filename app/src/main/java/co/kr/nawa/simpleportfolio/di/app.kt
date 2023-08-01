package co.kr.nawa.simpleportfolio.di

import co.kr.nawa.simpleportfolio.menu.viewModel.ListViewModel
import co.kr.nawa.simpleportfolio.menu.viewModel.LoginViewModel
import co.kr.nawa.simpleportfolio.menu.viewModel.MapViewModel
import co.kr.nawa.simpleportfolio.menu.viewModel.NfcViewModel
import co.kr.nawa.simpleportfolio.util.async.RestHelper
import co.kr.nawa.simpleportfolio.util.async.Repository
import co.kr.nawa.simpleportfolio.viewModel.MainViewModel
import co.kr.nawa.simpleportfolio.menu.viewModel.SubViewModel
import co.kr.nawa.simpleportfolio.viewModel.IntroViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.parameter.parametersOf
import org.koin.dsl.module

val app= module {

    factory {
            (type: RestHelper.Type)-> RestHelper(type)
    }

    factory {
            (type: RestHelper.Type)->
        Repository(get { parametersOf(type) })
    }

}

val viewModels= module {

    viewModel {
        IntroViewModel(get{ parametersOf(RestHelper.Type.SUB)})
    }

    viewModel {
        MainViewModel()
//        MainViewModel(get{ parametersOf(RestHelper.Type.MAIN) })
    }

    viewModel {
        SubViewModel()
    }

    viewModel {
        ListViewModel(get{ parametersOf(RestHelper.Type.MAIN)})
    }

    viewModel {
        LoginViewModel(get{ parametersOf(RestHelper.Type.MAIN)})
    }

    viewModel {
        MapViewModel(get{ parametersOf(RestHelper.Type.MAIN)})
    }

    viewModel {
        NfcViewModel()
    }

}

val appModule= listOf(app,viewModels)